import axios from "axios";
import dotenv from "dotenv";

dotenv.config({ path: "./src/.env" });

class ClimateService {
  constructor() {
    this.api_url = "https://apiadvisor.climatempo.com.br/api/v1";
    this.api_key = process.env.API_KEY;
  }

  async consultarUrl(endpoint) {
    const url = `${this.api_url}/${endpoint}?token=${this.api_key}`;
    try {
      const response = await axios.get(url);
      return response.data;
    } catch (error) {
      return {
        error: `Falha ao obter dados. Código de status: ${error.response.status}`,
      };
    }
  }

  async getClimate(country) {
    return this.consultarUrl(`anl/synoptic/locale/${country}`);
  }

  async getClimateRain(id) {
    return this.consultarUrl(`climate/temperature/locale/${id}`);
  }
}

export default ClimateService;
