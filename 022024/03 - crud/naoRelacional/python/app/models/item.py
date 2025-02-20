from bson import ObjectId

class Item:
    def __init__(self, name, description, price, _id=None):
        self._id = _id
        self.name = name
        self.description = description
        self.price = price

    def to_dict(self):
        item_dict = {
            "name": self.name,
            "description": self.description,
            "price": self.price
        }
        if self._id:
            item_dict["_id"] = str(self._id)  # Convertendo ObjectId para string
        return item_dict