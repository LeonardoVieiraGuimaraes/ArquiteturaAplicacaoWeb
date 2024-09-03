import { Request, Response } from "express";
import FipeService from "../service/fipe_service";

const fipeService = new FipeService();

export const consultarMarcas = async (req: Request, res: Response) => {
  const data = await fipeService.consultarMarcas();
  res.json(data);
};

export const consultarModelos = async (req: Request, res: Response) => {
  const marca = parseInt(req.params.marca);
  const data = await fipeService.consultarModelos(marca);
  res.json(data);
};

export const consultarAnos = async (req: Request, res: Response) => {
  const marca = parseInt(req.params.marca);
  const modelo = parseInt(req.params.modelo);
  const data = await fipeService.consultarAnos(marca, modelo);
  res.json(data);
};

export const consultarValor = async (req: Request, res: Response) => {
  const marca = parseInt(req.params.marca);
  const modelo = parseInt(req.params.modelo);
  const ano = req.params.ano;
  const data = await fipeService.consultarValor(marca, modelo, ano);
  res.json(data);
};
