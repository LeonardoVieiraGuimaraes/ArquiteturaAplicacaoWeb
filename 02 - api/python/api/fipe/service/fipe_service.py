# fipe/services/fipe_service.py
import requests

class FipeService:
    def consultar_url(self, api_url):
        response = requests.get(api_url)
        if response.status_code == 200:
            return response.json()
        else:
            return {"error": f"Falha ao obter dados. Código de status: {response.status_code}"}

    def consultar_marcas(self):
        return self.consultar_url("https://parallelum.com.br/fipe/api/v1/carros/marcas")

    def consultar_modelos(self, id):
        return self.consultar_url(f"https://parallelum.com.br/fipe/api/v1/carros/marcas/{id}/modelos")

    def consultar_anos(self, marca, modelo):
        return self.consultar_url(f"https://parallelum.com.br/fipe/api/v1/carros/marcas/{marca}/modelos/{modelo}/anos")

    def consultar_valor(self, marca, modelo, ano):
        return self.consultar_url(f"https://parallelum.com.br/fipe/api/v1/carros/marcas/{marca}/modelos/{modelo}/anos/{ano}")