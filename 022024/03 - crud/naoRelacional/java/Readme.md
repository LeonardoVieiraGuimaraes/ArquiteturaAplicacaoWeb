
Configurar o projeto Spring Boot:

Criar um novo projeto Spring Boot usando Spring Initializr.
Adicionar dependências para Spring Data MongoDB, Spring Web e Lombok.
Configurar a conexão com o MongoDB:

Adicionar as configurações de conexão no arquivo application.properties.
Criar a entidade:

Definir uma classe de entidade que será armazenada no MongoDB, utilizando Lombok para reduzir o código boilerplate.
Criar o repositório:

Criar uma interface que estende MongoRepository para operações CRUD.
Criar o serviço:

Implementar a lógica de negócios no serviço.
Criar o controlador:

Criar endpoints REST para operações CRUD.
Passo 1: Configurar o projeto Spring Boot
Use o Spring Initializr para gerar o projeto com as seguintes dependências:

Spring Web
Spring Data MongoDB
Lombok
Passo 2: Configurar a conexão com o MongoDB
No arquivo application.properties, adicione as configurações de conexão: