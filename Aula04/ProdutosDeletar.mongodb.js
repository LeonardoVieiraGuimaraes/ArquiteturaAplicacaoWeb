/* global use, db */
// MongoDB Playground
// Use Ctrl+Space inside a snippet or a string literal to trigger completions.

const database = "Aula";
const collection = "Produtos";

// Create a new database.
use(database);

// Delete a single document in the collection.
db[collection].deleteOne({ item: "paper" });

// List all documents in the collection to verify deletion.
const remainingDocumentsAfterDeleteOne = db[collection].find().toArray();
print("Documentos restantes após deleteOne:");
printjson(remainingDocumentsAfterDeleteOne);

// // Delete multiple documents in the collection.
// db[collection].deleteMany({ status: "D" });

// // List all documents in the collection to verify deletion.
// const remainingDocumentsAfterDeleteManyStatusD = db[collection]
//   .find()
//   .toArray();
// print("Documentos restantes após deleteMany (status 'D'):");
// printjson(remainingDocumentsAfterDeleteManyStatusD);

// // Delete all documents in the collection.
// db[collection].deleteMany({});

// // List all documents in the collection to verify deletion.
// const remainingDocumentsAfterDeleteManyAll = db[collection].find().toArray();
// print("Documentos restantes após deleteMany (todos):");
// printjson(remainingDocumentsAfterDeleteManyAll);

// // Re-insert documents for further examples.
// db[collection].insertMany([
//   {
//     item: "canvas",
//     qty: 100,
//     size: { h: 28, w: 35.5, uom: "cm" },
//     status: "A",
//   },
//   { item: "journal", qty: 25, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
//   { item: "mat", qty: 85, size: { h: 27.9, w: 35.5, uom: "cm" }, status: "A" },
//   {
//     item: "mousepad",
//     qty: 25,
//     size: { h: 19, w: 22.85, uom: "cm" },
//     status: "P",
//   },
//   {
//     item: "notebook",
//     qty: 50,
//     size: { h: 8.5, w: 11, uom: "in" },
//     status: "P",
//   },
//   { item: "paper", qty: 100, size: { h: 8.5, w: 11, uom: "in" }, status: "D" },
//   {
//     item: "planner",
//     qty: 75,
//     size: { h: 22.85, w: 30, uom: "cm" },
//     status: "D",
//   },
//   {
//     item: "postcard",
//     qty: 45,
//     size: { h: 10, w: 15.25, uom: "cm" },
//     status: "A",
//   },
//   {
//     item: "sketchbook",
//     qty: 80,
//     size: { h: 14, w: 21, uom: "cm" },
//     status: "A",
//   },
//   {
//     item: "drawing pad",
//     qty: 95,
//     size: { h: 22.85, w: 30.5, uom: "cm" },
//     status: "A",
//   },
// ]);

// // Delete a document with multiple criteria.
// db[collection].deleteOne({ item: "notebook", status: "P" });

// // List all documents in the collection to verify deletion.
// const remainingDocumentsAfterDeleteOneMultipleCriteria = db[collection]
//   .find()
//   .toArray();
// print("Documentos restantes após deleteOne com múltiplos critérios:");
// printjson(remainingDocumentsAfterDeleteOneMultipleCriteria);

// // Delete documents with quantity less than 50.
// db[collection].deleteMany({ qty: { $lt: 50 } });

// // List all documents in the collection to verify deletion.
// const remainingDocumentsAfterDeleteManyQtyLessThan50 = db[collection]
//   .find()
//   .toArray();
// print("Documentos restantes após deleteMany com quantidade menor que 50:");
// printjson(remainingDocumentsAfterDeleteManyQtyLessThan50);
