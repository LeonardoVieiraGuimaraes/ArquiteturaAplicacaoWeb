/* global use, db */
// MongoDB Playground
// Use Ctrl+Space inside a snippet or a string literal to trigger completions.

const database = "Aula";
const collection = "Produtos";

// Create a new database.
use(database);

// List all documents in the collection.
const inventario = db[collection].find();
print("Itens cadastrados no inventário:");
printjson(inventario);

// List all documents in the collection.
const inventarioarray = db[collection].find().toArray();
print("Itens cadastrados no inventário:");
printjson(inventarioarray);

// List documents with status "D" in the collection.
const statusD = db[collection].find({ status: "D" }).toArray();
print("Itens com status 'D':");
printjson(statusD);

// List documents with status "A" or "D" in the collection.
const statusAD = db[collection].find({ status: { $in: ["A", "D"] } }).toArray();
print("Itens com status 'A' ou 'D':");
printjson(statusAD);

// List documents with status "A" and quantity less than 30 in the collection.
const statusAQtyLessThan30 = db[collection]
  .find({ status: "A", qty: { $lt: 30 } })
  .toArray();
print("Itens com status 'A' e quantidade menor que 30:");
printjson(statusAQtyLessThan30);
