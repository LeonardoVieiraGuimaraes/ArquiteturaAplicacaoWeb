import mongoose from "mongoose";

const ItemSchema = new mongoose.Schema({
  nome: {
    type: String,
    required: true,
  },
  descricao: {
    type: String,
    required: true,
  },
});

export default mongoose.model("Item", ItemSchema);
