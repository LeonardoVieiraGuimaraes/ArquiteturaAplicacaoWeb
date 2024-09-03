# fipe/controllers/fipe_controller.py
from ninja import NinjaAPI
from fipe.service.fipe_service import FipeService

api = NinjaAPI()
fipe_service = FipeService()

@api.get("/marcas")
def consultar_marcas(request):
    return fipe_service.consultar_marcas()

@api.get("/modelos/{marca}")
def consultar_modelos(request, marca: int):
    return fipe_service.consultar_modelos(marca)

@api.get("/anos/{marca}/{modelo}")
def consultar_anos(request, marca: int, modelo: int):
    return fipe_service.consultar_anos(marca, modelo)

@api.get("/valor/{marca}/{modelo}/{ano}")
def consultar_valor(request, marca: int, modelo: int, ano: str):
    return fipe_service.consultar_valor(marca, modelo, ano)