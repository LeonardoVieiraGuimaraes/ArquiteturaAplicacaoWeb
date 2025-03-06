# climate/controllers/climate_controller.py
from ninja import NinjaAPI

from climate.service.climate_service import ClimateService


api = NinjaAPI()
climate_service = ClimateService()

@api.get("/{country}")
def get_climate(request, country: str):
    return climate_service.get_climate(country)

@api.get("/rain/{id}")
def get_climate_rain(request, id: str):
    return climate_service.get_climate_rain(id)