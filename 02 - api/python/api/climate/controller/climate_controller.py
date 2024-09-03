# climate/controller/climate_controller.py
from ninja import NinjaAPI
from climate.services.climate_service import ClimateService

api = NinjaAPI()
climate_service = ClimateService()

@api.get("/{country}")
def get_climate(request, country: str):
    return climate_service.get_climate(country)

@api.get("/rain/{id}")
def get_climate_rain(request, id: str):
    return climate_service.get_climate_rain(id)



# # climate/services/climate_service.py
# import os

# class ClimateService:
#     def get_climate(self, country: str):
#         # Lógica para obter o clima usando a variável de ambiente API_KEY
#         api_key = os.getenv('API_KEY')
#         # Simulação de resposta sem retornar a API_KEY
#         return {"country": country, "climate": "sunny"}

#     def get_climate_rain(self, id: str):
#         # Lógica para obter informações de chuva usando a variável de ambiente API_KEY
#         api_key = os.getenv('API_KEY')
#         # Simulação de resposta sem retornar a API_KEY
#         return {"id": id, "rain": "moderate"}