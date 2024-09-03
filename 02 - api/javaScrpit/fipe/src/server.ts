import express from "express";
import fipeRouter from "./router/fipe_router";

const app = express();
const port = process.env.PORT || 3000;

app.use("/fipe", fipeRouter);

app.listen(port, () => {
  console.log(`Servidor rodando na porta http://localhost:${port}`);
});
