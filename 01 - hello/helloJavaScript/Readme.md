# Guia para Criar uma Aplicação Hello World com Node.js e Express

## Introdução
Bem-vindo ao guia passo a passo para criar sua primeira aplicação "Hello World" usando Node.js e Express. Vamos explorar como configurar seu ambiente de desenvolvimento e criar uma aplicação RESTful simples que exibe "Hello World" no navegador.

## Pré-requisitos
Antes de começarmos, certifique-se de ter as seguintes ferramentas instaladas:
- [Node.js e npm](https://nodejs.org/)

## Configuração do Ambiente

### Instalando o Node.js e npm
1. Baixe e instale o Node.js a partir do [site oficial](https://nodejs.org/).
2. Verifique a instalação abrindo o terminal e digitando:
   ```sh
   node -v
   npm -v
    ```

# Criando a Aplicação Express

1. **Crie um diretório para o seu projeto e navegue até ele:**

    ```sh
    mkdir hello-express
    cd hello-express
    ```
2. **Inicialize um novo projeto Node.js:**

    ```sh
    npm init -y
    ```
3. **Instale o Express:**

    ```sh
    npm install express
    ```

# Implementando o Hello World

## Criando o Servidor Express
### No diretório do projeto, crie um arquivo chamado index.js com o seguinte conteúdo:

 ```sh
    const express = require('express');
const app = express();
const port = 3000;

app.get('/', (req, res) => {
  res.send('Hello World');
});

app.listen(port, () => {
  console.log(`Servidor rodando em http://localhost:${port}`);
});
```

## Explicação
1. **const express = require('express');: Importa o módulo Express.**

2. **const app = express();: Cria uma instância do aplicativo Express.**

3. **app.get('/', (req, res) => { res.send('Hello World'); });: Define uma rota GET que retorna "Hello World".**

4. **app.listen(port, () => { console.log(Servidor rodando em http://localhost:${port}`); });`: Inicia o servidor na porta especificada.**

### No terminal, execute o comando:

```sh
    node index.js
```

### Abra o navegador e acesse http://localhost:3000. Você verá "Hello World" exibido na página.