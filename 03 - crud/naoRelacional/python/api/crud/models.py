from django.db import models
from pydantic import BaseModel

class ItemSchema(BaseModel):
    name: str
    description: str
    price: float

class ItemOutSchema(ItemSchema):
    id: str

# Modelo Django para fins de administração
class ItemAdminModel(models.Model):
    name = models.CharField(max_length=255)
    description = models.TextField()
    price = models.FloatField()

    def __str__(self):
        return self.name