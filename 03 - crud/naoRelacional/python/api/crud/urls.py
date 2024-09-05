from django.contrib import admin
from django.urls import path
from ninja import NinjaAPI
from items.api import router as items_router

api = NinjaAPI()
api.add_router('/items/', items_router)

urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/', api.urls),
]