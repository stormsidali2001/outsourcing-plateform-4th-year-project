

from fastapi import FastAPI
from confluent_kafka import Consumer
kafka_config = {
    'bootstrap.servers': 'localhost:9092',
    'group.id': 'default',
    'auto.offset.reset': 'latest'
}
consumer = Consumer(kafka_config)
app = FastAPI()


@app.get("/")
async def root():
    return {"message": "Periodic task scheduled."}
