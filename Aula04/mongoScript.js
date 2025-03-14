const { MongoClient } = require("mongodb");
const uri = "your_mongodb_connection_string";
const client = new MongoClient(uri);

async function run() {
  try {
    await client.connect();
    const database = client.db("database_name");
    const collection = database.collection("collection_name");

    // Inserir Documento
    const insertResult = await collection.insertOne({
      nome: "João",
      idade: 30,
    });
    console.log(`Documento inserido com o _id: ${insertResult.insertedId}`);

    // Listar Documentos
    const documents = await collection.find({}).toArray();
    console.log("Documentos:", documents);

    // Atualizar Documento
    const updateResult = await collection.updateOne(
      { nome: "João" },
      { $set: { idade: 31 } }
    );
    console.log(`Documentos atualizados: ${updateResult.modifiedCount}`);

    // Deletar Documento
    const deleteResult = await collection.deleteOne({ nome: "João" });
    console.log(`Documentos deletados: ${deleteResult.deletedCount}`);
  } finally {
    await client.close();
  }
}

run().catch(console.dir);
