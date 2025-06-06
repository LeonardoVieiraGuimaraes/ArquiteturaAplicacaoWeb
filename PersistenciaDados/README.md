# Introdução ao MongoDB

MongoDB é um banco de dados NoSQL orientado a documentos que utiliza documentos no formato JSON com esquemas dinâmicos. É conhecido por sua escalabilidade e flexibilidade.

## Instalação do MongoDB

### Windows

1. Baixe o instalador do MongoDB no site oficial: [MongoDB Download Center](https://www.mongodb.com/try/download/community)
2. Execute o instalador e siga as instruções na tela.
3. Após a instalação, adicione o diretório `bin` do MongoDB ao PATH do sistema.

### Linux

```sh
# Importar a chave pública usada pelo sistema de gerenciamento de pacotes
wget -qO - https://www.mongodb.org/static/pgp/server-4.4.asc | sudo apt-key add -

# Criar o arquivo de lista para o MongoDB
echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/4.4 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-4.4.list

# Atualizar a lista de pacotes
sudo apt-get update

# Instalar o MongoDB
sudo apt-get install -y mongodb-org
```

### macOS

```sh
# Instalar o MongoDB usando Homebrew
brew tap mongodb/brew
brew install mongodb-community@4.4
```

## Comandos Básicos do MongoDB

### Iniciar o MongoDB

```sh
# Iniciar o serviço do MongoDB
mongod
```

### Conectar ao MongoDB

```sh
# Conectar ao MongoDB usando o shell
mongo
```

### Comandos CRUD

```sh
# Selecionar um banco de dados
use meuBancoDeDados

# Inserir um documento
db.minhaColecao.insertOne({ nome: "João", idade: 30 })

# Encontrar um documento
db.minhaColecao.findOne({ nome: "João" })

# Atualizar um documento
db.minhaColecao.updateOne({ nome: "João" }, { $set: { idade: 31 } })

# Deletar um documento
db.minhaColecao.deleteOne({ nome: "João" })
```

## Exemplo de Implementação em Node.js

Veja o arquivo `index.js` para um exemplo de implementação em Node.js utilizando a biblioteca `mongodb`.

# Aula 04 - Implementação do MongoDB

## Configuração do MongoDB

1. Instale o MongoDB seguindo as instruções em: https://docs.mongodb.com/manual/installation/
2. Inicie o serviço do MongoDB.
3. Instale as dependências necessárias no seu projeto:
   ```bash
   npm install mongodb
   ```

## Uso

### Inserir Documento

```javascript
const { MongoClient } = require('mongodb');
const uri = "your_mongodb_connection_string";
const client = new MongoClient(uri);

async function insertDocument(document) {
    try {
        await client.connect();
        const database = client.db('database_name');
        const collection = database.collection('collection_name');
        const result = await collection.insertOne(document);
        console.log(`Documento inserido com o _id: ${result.insertedId}`);
    } finally {
        await client.close();
    }
}
```

### Listar Documentos

```javascript
async function listDocuments() {
    try {
        await client.connect();
        const database = client.db('database_name');
        const collection = database.collection('collection_name');
        const documents = await collection.find({}).toArray();
        console.log(documents);
    } finally {
        await client.close();
    }
}
```

### Atualizar Documento

```javascript
async function updateDocument(filter, update) {
    try {
        await client.connect();
        const database = client.db('database_name');
        const collection = database.collection('collection_name');
        const result = await collection.updateOne(filter, { $set: update });
        console.log(`Documentos atualizados: ${result.modifiedCount}`);
    } finally {
        await client.close();
    }
}
```

### Deletar Documento

```javascript
async function deleteDocument(filter) {
    try {
        await client.connect();
        const database = client.db('database_name');
        const collection = database.collection('collection_name');
        const result = await collection.deleteOne(filter);
        console.log(`Documentos deletados: ${result.deletedCount}`);
    } finally {
        await client.close();
    }
}
```

## Usando a Extensão "MongoDB for VS Code"

1. Instale a extensão "MongoDB for VS Code" no Visual Studio Code.
2. Configure a conexão com seu banco de dados MongoDB na extensão.
3. Abra o arquivo `mongoScript.js` no VSCode.
4. Clique com o botão direito no arquivo e selecione "Run MongoDB Script" para executar o script diretamente no MongoDB.

O arquivo `mongoScript.js` contém um exemplo de como se conectar ao MongoDB e realizar operações CRUD diretamente.


show collections


db.nomeDaColecao.drop()

use nomeDoBanco
db.dropDatabase()


Criação de Banco de Dados e Coleções:

use(database): Seleciona ou cria o banco de dados "Aula".
db.createCollection(collection): Cria a coleção "Produtos".
Inserção de Documentos:

insertMany: Insere múltiplos documentos na coleção "Produtos".
Listagem de Documentos:

find(): Retorna todos os documentos da coleção.
toArray(): Converte o cursor em um array para exibição.
printjson(): Exibe os documentos no console em formato JSON.
Filtragem de Documentos:

find({ status: "D" }): Retorna os documentos que têm o campo status igual a "D".
find({ status: { $in: ["A", "D"] } }): Retorna os documentos que têm o campo status igual a "A" ou "D".
find({ status: "A", qty: { $lt: 30 } }): Retorna os documentos que têm o campo status igual a "A" e quantidade (qty) menor que 30.