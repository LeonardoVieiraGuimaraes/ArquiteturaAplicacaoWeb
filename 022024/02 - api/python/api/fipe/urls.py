# fipe/urls.py
from django.urls import path
from fipe.controller.fipe_controller import api

urlpatterns = [
    path('', api.urls),
]