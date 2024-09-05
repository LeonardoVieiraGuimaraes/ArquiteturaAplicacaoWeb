import express from "express";
import {
  createItem,
  getAllItems,
  getItemById,
  updateItem,
  deleteItem,
} from "../controllers/itemController.js";

const router = express.Router();

router.post("/itens", createItem);
router.get("/itens", getAllItems);
router.get("/itens/:id", getItemById);
router.patch("/itens/:id", updateItem);
router.delete("/itens/:id", deleteItem);

export default router;
