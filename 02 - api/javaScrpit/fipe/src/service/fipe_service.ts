import axios from "axios";

class FipeService {
  private apiUrl: string;

  constructor() {
    this.apiUrl = "https://parallelum.com.br/fipe/api/v1";
  }

  private async consultarUrl(endpoint: string) {
    const url = `${this.apiUrl}/${endpoint}`;
    try {
      const response = await axios.get(url);
      return response.data;
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error("Erro na requisição:", error.response?.data);
      } else if (error instanceof Error) {
        console.error("Erro:", error.message);
      } else {
        console.error("Erro desconhecido", error);
      }
      throw error; // Re-lançar o erro após o log, se necessário
    }
  }

  public consultarMarcas() {
    return this.consultarUrl("carros/marcas");
  }

  public consultarModelos(marca: number) {
    return this.consultarUrl(`carros/marcas/${marca}/modelos`);
  }

  public consultarAnos(marca: number, modelo: number) {
    return this.consultarUrl(`carros/marcas/${marca}/modelos/${modelo}/anos`);
  }

  public consultarValor(marca: number, modelo: number, ano: string) {
    return this.consultarUrl(
      `carros/marcas/${marca}/modelos/${modelo}/anos/${ano}`
    );
  }
}

export default FipeService;
