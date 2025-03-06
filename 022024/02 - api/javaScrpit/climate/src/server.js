import express from "express";
import climateController from "./controller/climate_controller.js";

const app = express();
const port = process.env.PORT || 3000;

app.use("/climate", climateController);

app.listen(port, () => {
  console.log(`Servidor rodando na porta http://localhost:${port}`);
});
