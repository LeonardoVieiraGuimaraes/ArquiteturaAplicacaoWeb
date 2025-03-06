from bson.objectid import ObjectId
from models.item import Item

class ItemService:
    def __init__(self, mongo):
        self.collection = mongo.db.items

    def create_item(self, data):
        item = Item(**data)
        result = self.collection.insert_one(item.to_dict())
        return result

    def get_items(self):
        items = self.collection.find()
        return [Item(**item) for item in items]

    def get_item(self, id):
        item = self.collection.find_one({"_id": ObjectId(id)})
        if item:
            return Item(**item)
        return None

    def update_item(self, id, data):
        result = self.collection.update_one({"_id": ObjectId(id)}, {"$set": data})
        if result.matched_count > 0:
            return self.get_item(id)
        return None

    def delete_item(self, id):
        result = self.collection.delete_one({"_id": ObjectId(id)})
        return result