import {
  createItem as createItemService,
  getAllItems as getAllItemsService,
  getItemById as getItemByIdService,
  updateItem as updateItemService,
  deleteItem as deleteItemService,
} from "../services/ItemService.js";

export const createItem = async (req, res) => {
  try {
    const item = await createItemService(req.body);
    res.status(201).send(item);
  } catch (err) {
    res.status(400).send(err);
  }
};

export const getAllItems = async (req, res) => {
  try {
    const items = await getAllItemsService();
    res.status(200).send(items);
  } catch (err) {
    res.status(500).send(err);
  }
};

export const getItemById = async (req, res) => {
  try {
    const item = await getItemByIdService(req.params.id);
    if (!item) {
      return res.status(404).send();
    }
    res.status(200).send(item);
  } catch (err) {
    res.status(500).send(err);
  }
};

export const updateItem = async (req, res) => {
  try {
    const item = await updateItemService(req.params.id, req.body);
    if (!item) {
      return res.status(404).send();
    }
    res.status(200).send(item);
  } catch (err) {
    res.status(400).send(err);
  }
};

export const deleteItem = async (req, res) => {
  try {
    const item = await deleteItemService(req.params.id);
    if (!item) {
      return res.status(404).send();
    }
    res.status(200).send(item);
  } catch (err) {
    res.status(500).send(err);
  }
};
