# climate/services/climate_service.py
import os

class ClimateService:
    def get_climate(self, country: str):
        # Lógica para obter o clima usando a variável de ambiente API_KEY
        api_key = os.getenv('API_KEY')
        # Simulação de resposta
        return {"country": country, "climate": "sunny", "api_key": api_key}

    def get_climate_rain(self, id: str):
        # Lógica para obter informações de chuva usando a variável de ambiente API_KEY
        api_key = os.getenv('API_KEY')
        # Simulação de resposta
        return {"id": id, "rain": "moderate", "api_key": api_key}