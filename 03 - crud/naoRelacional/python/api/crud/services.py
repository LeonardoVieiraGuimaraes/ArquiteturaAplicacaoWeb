from pymongo import MongoClient
from bson import ObjectId
from typing import List, Optional
from django.conf import settings
from .models import ItemSchema, ItemOutSchema

client = MongoClient(settings.MONGO_URI)
db = client.myDatabase
collection = db.items

class ItemService:
    @staticmethod
    def create_item(data: ItemSchema) -> ItemOutSchema:
        result = collection.insert_one(data.dict())
        return ItemOutSchema(id=str(result.inserted_id), **data.dict())

    @staticmethod
    def list_items() -> List[ItemOutSchema]:
        items = collection.find()
        return [ItemOutSchema(id=str(item["_id"]), **item) for item in items]

    @staticmethod
    def get_item(item_id: str) -> Optional[ItemOutSchema]:
        item = collection.find_one({"_id": ObjectId(item_id)})
        if item:
            return ItemOutSchema(id=str(item["_id"]), **item)
        return None

    @staticmethod
    def update_item(item_id: str, data: ItemSchema) -> Optional[ItemOutSchema]:
        collection.update_one({"_id": ObjectId(item_id)}, {"$set": data.dict()})
        item = collection.find_one({"_id": ObjectId(item_id)})
        if item:
            return ItemOutSchema(id=str(item["_id"]), **item)
        return None

    @staticmethod
    def delete_item(item_id: str) -> bool:
        result = collection.delete_one({"_id": ObjectId(item_id)})
        return result.deleted_count > 0