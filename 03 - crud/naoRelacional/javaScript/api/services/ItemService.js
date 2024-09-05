import Item from "../models/Item.js";

export const createItem = async (data) => {
  const item = new Item(data);
  return await item.save();
};

export const getAllItems = async () => {
  return await Item.find();
};

export const getItemById = async (id) => {
  return await Item.findById(id);
};

export const updateItem = async (id, data) => {
  return await Item.findByIdAndUpdate(id, data, {
    new: true,
    runValidators: true,
  });
};

export const deleteItem = async (id) => {
  return await Item.findByIdAndDelete(id);
};
