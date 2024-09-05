from ninja import Router
from typing import List
from .services import ItemService
from .models import ItemSchema, ItemOutSchema

router = Router()

@router.post("/", response=ItemOutSchema)
def create_item(request, item: ItemSchema):
    return ItemService.create_item(item)

@router.get("/", response=List[ItemOutSchema])
def list_items(request):
    return ItemService.list_items()

@router.get("/{item_id}", response=ItemOutSchema)
def get_item(request, item_id: str):
    item = ItemService.get_item(item_id)
    if item:
        return item
    return {"error": "Item not found"}

@router.put("/{item_id}", response=ItemOutSchema)
def update_item(request, item_id: str, data: ItemSchema):
    item = ItemService.update_item(item_id, data)
    if item:
        return item
    return {"error": "Item not found"}

@router.delete("/{item_id}")
def delete_item(request, item_id: str):
    if ItemService.delete_item(item_id):
        return {"message": "Item deleted"}
    return {"error": "Item not found"}