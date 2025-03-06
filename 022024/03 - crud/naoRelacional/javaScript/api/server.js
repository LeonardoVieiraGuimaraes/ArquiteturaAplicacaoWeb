import express from "express";
import mongoose from "mongoose";
import bodyParser from "body-parser";
import itemRouter from "./routers/itemRouter.js";

const app = express();
const port = 3000;

// Middleware
app.use(bodyParser.json());

// Conexão com o MongoDB
mongoose
  .connect("mongodb://localhost:27017/myDatabase")
  .then(() => {
    console.log("Conectado ao MongoDB");
  })
  .catch((err) => {
    console.error("Erro ao conectar ao MongoDB", err);
  });

// Rotas
app.use("/api", itemRouter);

// Iniciar o servidor
app.listen(port, () => {
  console.log(`Servidor rodando na porta http://localhost:${port}`);
});
