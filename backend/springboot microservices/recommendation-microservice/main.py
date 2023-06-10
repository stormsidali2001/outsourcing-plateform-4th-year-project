

import json
from fastapi import FastAPI
from confluent_kafka import Consumer
import asyncio

kafka_config = {
    'bootstrap.servers': 'localhost:9092',
    'group.id': 'default',
    'auto.offset.reset': 'latest'
}
consumer = Consumer(kafka_config)

async def consume_messages():
    consumer.subscribe(["interaction-added"])  # Subscribe to the Kafka topic

    while True:
        message = consumer.poll(1.0)  # Consume a message from Kafka topic
        if message is not None:
            message_value = message.value().decode()
            try:
                message_json = json.loads(message_value)
                company_id = message_json.get("companyId")
                worker_id = message_json.get("workerId")
                message_type = message_json.get("type")
                print(f"Received message: companyId={company_id}, workerId={worker_id}, type={message_type}")
            except json.JSONDecodeError:
                print("Invalid message format")

        await asyncio.sleep(0.1)  # Pause briefly to allow other tasks to run


app = FastAPI()

@app.on_event("startup")
async def startup_event():
    asyncio.create_task(consume_messages())  # Start consuming messages in the background

@app.get("/")
async def root():
    return {"message": "Periodic task scheduled."}
