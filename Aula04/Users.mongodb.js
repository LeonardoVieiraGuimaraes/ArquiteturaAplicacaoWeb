/* global use, db */
// MongoDB Playground
// Use Ctrl+Space inside a snippet or a string literal to trigger completions.

const database = "Aula";
const collection = "Users";

// // Create a new database.
use(database);

// // Create a new collection.
db.createCollection(collection);

// Insert a single document with user data.
db[collection].insertOne({
  nome: "Daniel Silva e Silva",
  idade: 20,
  pais: "Brasil",
  estado: "SP",
  cidade: "São Paulo",
});

// // Insert multiple documents into the collection.
// db[collection].insertMany([
//   {
//     item: "journal",
//     qty: 25,
//     tags: ["blank", "red"],
//     tamanho: { h: 14, w: 21, uom: "cm" },
//   },
//   {
//     item: "mat",
//     qty: 85,
//     tags: ["grey"],
//     tamanho: { h: 27.9, w: 35.5, uom: "cm" },
//   },
//   {
//     item: "mousepad",
//     qty: 25,
//     tags: ["gel", "blue"],
//     tamanho: { h: 19, w: 22.85, uom: "cm" },
//   },
// ]);

// List all documents in the collection.
const inventario = db[collection].find().toArray();
print("Itens cadastrados no inventário:");
printjson(inventario);

// // Listar todos os documentos na coleção "Users"
// print("Usuários cadastrados:");
// printjson(db.Users.find().pretty());

// The prototype form to create a collection:
/* db.createCollection( <name>,
  {
    capped: <boolean>,
    autoIndexId: <boolean>,
    size: <number>,
    max: <number>,
    storageEngine: <document>,
    validator: <document>,
    validationLevel: <string>,
    validationAction: <string>,
    indexOptionDefaults: <document>,
    viewOn: <string>,
    pipeline: <pipeline>,
    collation: <document>,
    writeConcern: <document>,
    timeseries: { // Added in MongoDB 5.0
      timeField: <string>, // required for time series collections
      metaField: <string>,
      granularity: <string>,
      bucketMaxSpanSeconds: <number>, // Added in MongoDB 6.3
      bucketRoundingSeconds: <number>, // Added in MongoDB 6.3
    },
    expireAfterSeconds: <number>,
    clusteredIndex: <document>, // Added in MongoDB 5.3
  }
)*/

// More information on the `createCollection` command can be found at:
// https://www.mongodb.com/docs/manual/reference/method/db.createCollection/
