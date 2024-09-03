# climate/urls.py
from django.urls import path
from climate.controller.climate_controller import api

urlpatterns = [
    path('', api.urls),
]