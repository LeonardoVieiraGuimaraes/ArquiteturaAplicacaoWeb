import express from "express";
import {
  consultarMarcas,
  consultarModelos,
  consultarAnos,
  consultarValor,
} from "../controller/fipe_controller";

const router = express.Router();

router.get("/marcas", consultarMarcas);
router.get("/modelos/:marca", consultarModelos);
router.get("/anos/:marca/:modelo", consultarAnos);
router.get("/valor/:marca/:modelo/:ano", consultarValor);

export default router;
