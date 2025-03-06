import express from "express";
import ClimateService from "../service/climate_service.js";

const router = express.Router();
const climateService = new ClimateService();

router.get("/:country", async (req, res) => {
  const country = req.params.country;
  const data = await climateService.getClimate(country);
  res.json(data);
});

router.get("/rain/:id", async (req, res) => {
  const id = req.params.id;
  const data = await climateService.getClimateRain(id);
  res.json(data);
});

export default router;
