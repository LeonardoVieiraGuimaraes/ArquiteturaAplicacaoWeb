# climate/services/climate_service.py
import os
import requests
from dotenv import load_dotenv

load_dotenv()

class ClimateService:
    def __init__(self):
        self.api_url =  "https://apiadvisor.climatempo.com.br/api/v1";
        self.api_key = os.getenv('API_KEY')

    def consultar_url(self, endpoint):
        url = f"{self.api_url}/{endpoint}?token={self.api_key}"
        response = requests.get(url)
        if response.status_code == 200:
            return response.json()
        else:
            return {"error": f"Falha ao obter dados. Código de status: {response.status_code}"}

    def get_climate(self, country):
        return self.consultar_url(f"anl/synoptic/locale/{country}")

    def get_climate_rain(self, id):
        return self.consultar_url(f"climate/temperature/locale/{id}")