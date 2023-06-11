import json
from fastapi import FastAPI
import asyncio
import tensorflow as tf
import pandas as pd
import uvicorn
from confluent_kafka import Consumer

class CollaborativeFilteringModel:
    def __init__(self, num_users, num_items, embedding_dim=50):
        self.embedding_dim = embedding_dim
        self.user_embedding = tf.Variable(tf.random.normal([num_users, embedding_dim]))
        self.item_embedding = tf.Variable(tf.random.normal([num_items, embedding_dim]))
        self.user_id_to_index = {}
        self.item_id_to_index = {}
        self.index_to_user_id = {}
        self.index_to_item_id = {}
        self.next_user_index = 0
        self.next_item_index = 0
        

    def get_or_create_user_index(self, user_id):
            if user_id in self.user_id_to_index:
                return self.user_id_to_index[user_id]
            else:
                index = self.next_user_index
                self.user_id_to_index[user_id] = index
                self.next_user_index += 1
                return index

    def get_or_create_item_index(self, item_id):
        if item_id in self.item_id_to_index:
            return self.item_id_to_index[item_id]
        else:
            index = self.next_item_index
            self.item_id_to_index[item_id] = index
            self.next_item_index += 1
            return index

    def train(self, new_interactions):
        # Update the user and item embeddings based on the new interactions
        for new_interaction in new_interactions:
            user_id = new_interaction["uid"]
            item_id = new_interaction["iid"]
            
            user_index = self.get_or_create_user_index(user_id)
            item_index = self.get_or_create_item_index(item_id)

            user_embedding = self.user_embedding[user_index]
            item_embedding = self.item_embedding[item_index]

            predicted_rating = tf.reduce_sum(user_embedding * item_embedding)

            # Update the embeddings using gradient descent
            with tf.GradientTape() as tape:
                loss = tf.square(new_interaction["rating"] - predicted_rating)
                print(f" losss value is : {loss}")
                gradients = tape.gradient(loss, [self.user_embedding, self.item_embedding])
                learning_rate = 0.01
                print(f" Gradients : {gradients}")
                print(f"Gradient 0: {gradients[0]}")
                print(f"Gradient 1: {gradients[1]}")
                self.user_embedding.assign_sub(learning_rate * gradients[0])
                self.item_embedding.assign_sub(learning_rate * gradients[1])

    def predict(self, worker_id, company_id):
        user_embedding = self.user_embedding[worker_id]
        item_embedding = self.item_embedding[company_id]
        predicted_rating = tf.reduce_sum(user_embedding * item_embedding)
        return predicted_rating.numpy()

    def get_factorization_matrix(self):
        factorization_matrix = tf.matmul(self.user_embedding, self.item_embedding, transpose_b=True)
        return factorization_matrix.numpy()           
            
            
kafka_config = {
    'bootstrap.servers': 'localhost:9092',
    'group.id': 'default',
    'auto.offset.reset': 'latest'
}
interactionTypeToRating = {
    "IMPRESSION":0.3,
    "CLICK":2,
    "WISH":5
}
consumer = Consumer(kafka_config)

# Create a recommender app
app = FastAPI()

# Create a recommender model
recommender_model = CollaborativeFilteringModel(num_users=50,num_items=50)


async def consume_messages():
    consumer.subscribe(["interaction-added"])

    while True:
        message = consumer.poll(1.0)
        if message is not None:
            message_value = message.value().decode()
            try:
                message_json = json.loads(message_value)
                print(message_json)
                company_id = message_json.get("companyId")
                worker_id = message_json.get("workerId")
                interactionType = message_json.get("interactionType")
                print(f"Received message: companyId={company_id}, workerId={worker_id}, type={interactionType}")
                new_interaction = {
                    "uid": company_id,
                    "iid": worker_id,
                    "rating": interactionTypeToRating[interactionType]
                }
                recommender_model.train([new_interaction])
                predicted_rating = recommender_model.predict(worker_id, company_id)
                print(f"Predicted rating: {predicted_rating}")
            except json.JSONDecodeError:
                print("Invalid message format")

        await asyncio.sleep(0.1)


@app.on_event("startup")
async def startup_event():
    asyncio.create_task(consume_messages())
    

@app.get("/")
async def root():
    print("/")
    return {"message": "Periodic task scheduled."}

@app.get("/factorization-matrix")
async def get_factorization_matrix():
    factorization_matrix = recommender_model.get_factorization_matrix()
    return factorization_matrix.tolist()