/* global use, db */
// MongoDB Playground
// Use Ctrl+Space inside a snippet or a string literal to trigger completions.

const database = "Aula";
const collection = "Produtos";

// Create a new database.
use(database);

// Update a document in the collection.
db[collection].updateOne(
  { item: "paper" },
  {
    $set: { "size.uom": "cm", status: "P" },
    $currentDate: { lastModified: true },
  }
);

// List the updated document.
const updatedPaper = db[collection].find({ item: "paper" }).toArray();
print("Documento atualizado:");
printjson(updatedPaper);
