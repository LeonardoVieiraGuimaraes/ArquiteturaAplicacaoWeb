from django.contrib import admin
from django.utils.html import format_html
from .models import ItemAdminModel
from pymongo import MongoClient
from django.conf import settings

class MongoDBItemAdmin(admin.ModelAdmin):
    list_display = ('name', 'description', 'price')
    search_fields = ('name', 'description')

    def changelist_view(self, request, extra_context=None):
        client = MongoClient(settings.MONGO_URI)
        db = client.myDatabase
        collection = db.items
        items = collection.find()
        extra_context = extra_context or {}
        extra_context['items'] = items
        return super().changelist_view(request, extra_context=extra_context)

    def name(self, obj):
        return obj['name']

    def description(self, obj):
        return obj['description']

    def price(self, obj):
        return obj['price']

admin.site.register(ItemAdminModel, MongoDBItemAdmin)