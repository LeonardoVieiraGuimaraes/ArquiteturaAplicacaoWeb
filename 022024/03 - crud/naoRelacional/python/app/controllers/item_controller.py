from flask import Blueprint, request, jsonify
from services.item_service import ItemService

item_bp = Blueprint('item_bp', __name__)
item_service = None

def init_app(mongo):
    global item_service
    item_service = ItemService(mongo)

@item_bp.route('/', methods=['POST'])
def create_item():
    data = request.get_json()
    result = item_service.create_item(data)
    return jsonify({'_id': str(result.inserted_id)}), 201

@item_bp.route('/', methods=['GET'])
def get_items():
    items = item_service.get_items()
    return jsonify([item.to_dict() for item in items])

@item_bp.route('/<id>', methods=['GET'])
def get_item(id):
    item = item_service.get_item(id)
    if item:
        return jsonify(item.to_dict())
    else:
        return jsonify({'error': 'Item not found'}), 404

@item_bp.route('/<id>', methods=['PUT'])
def update_item(id):
    data = request.get_json()
    updated_item = item_service.update_item(id, data)
    if updated_item:
        return jsonify(updated_item.to_dict())
    else:
        return jsonify({'error': 'Item not found'}), 404

@item_bp.route('/<id>', methods=['DELETE'])
def delete_item(id):
    result = item_service.delete_item(id)
    if result.deleted_count > 0:
        return jsonify({'message': 'Item deleted'}), 200
    else:
        return jsonify({'error': 'Item not found'}), 404