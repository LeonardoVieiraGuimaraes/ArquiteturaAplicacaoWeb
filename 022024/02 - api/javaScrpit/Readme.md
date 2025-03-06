 # Inicie o gerenciador de pacotes de projetos
  $ npm init -y

  # Instale no projeto as dependencias de desenvolvimento abaixo
  $ npm i typescript @types/node ts-node-dev -D

  # Crie o arquivo de configuração do typescript tsconfig.json
  $ npx tsc --init


     "alvo": "es2020"

      "rootDir": "./src",

         "outDir": "./dist",

            "dev": "ts-node-dev src/server.ts",


  # Instalar o ExpressJS
  $ npm i express

  # Instalar os types do ExpressJS como dependencia de projeto
  $ npm i -D @types/express

mkdir node-fipe-app
cd node-fipe-app
npm init -y
npm install express axios typescript ts-node @types/node @types/express