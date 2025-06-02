# Documentação de Aplicação Web Orientada a Serviços

---

## Objetivo de Aprendizagem

Esta unidade tem como objetivo ensinar a documentar uma aplicação web orientada a serviços por meio do diagrama da arquitetura, fluxos e documentação de rotas.

---

## Tópicos de Estudo

- Diagrama da arquitetura
- Fluxos de solicitação, endpoints e dependências
- Documentando as rotas

---

## Iniciando os Estudos

O desenvolvimento de uma aplicação envolve uma ampla gama de conhecimentos, pois é necessário não apenas trabalhar com elementos tecnológicos, mas também com elementos humanos, uma vez que as APIs são desenvolvidas por equipes de desenvolvedores e são utilizadas por outras pessoas.

Nesse sentido, é importante a divisão que ocorre na arquitetura cliente-servidor, pois permite o desenvolvimento independente em cada um dos lados. Além disso, a fim de se ganhar agilidade de um código, é fundamental que ele seja bem documentado para trazer velocidade e impessoalidade no desenvolvimento.

Esses são os temas centrais dessa unidade.

> **Seja bem-vindo e bons estudos!**

---

# 1. Diagrama da Arquitetura

## O que é o modelo cliente-servidor?

O modelo cliente-servidor consiste na arquitetura mais utilizada para aplicações baseadas em redes e, de acordo com Fielding (2000), nessa arquitetura, o lado servidor é responsável por fornecer e receber um conjunto de serviços e as solicitações sobre eles, enquanto o lado cliente realiza solicitações de serviço por meio de uma conexão. No entanto, cabe ao lado servidor decidir se aceita ou rejeita a solicitação, enviando assim uma resposta ao cliente.

A figura a seguir ilustra esse modelo, em que o lado cliente são os notebooks e o lado servidor é o computador ao centro da imagem, enquanto os serviços de internet estão representados pelo globo.

### Componentes do Modelo Cliente-Servidor

Segundo Andrews (1991):

- **Cliente:** processo acionador.
- **Servidor:** processo reativo.
- Clientes realizam solicitações que desencadeiam reações nos servidores.
- Clientes iniciam atividade no momento de sua escolha, podendo haver atraso até que sua solicitação seja atendida.
- O servidor espera que as solicitações sejam feitas e depois reage a elas.

### Saiba Mais

Segundo Fielding (2000), a separação entre cliente e servidor é importante, pois visa, no lado do servidor, simplificar esse componente de modo a melhorar a escalabilidade. Para isso, essa simplificação move toda a funcionalidade da interface do usuário para o componente cliente. Além disso, essa separação também permite que os dois lados evoluam independentemente, desde que a interface não seja alterada.

A forma básica do modelo cliente-servidor faz restrições de forma que o estado do aplicativo é dividido entre os componentes do cliente e do servidor, sendo frequentemente referida pelos mecanismos empregados para a implementação do conector como chamada de procedimento remoto ou middleware orientado a mensagens.

#### Vídeo recomendado

- **Título:** Arquitetura cliente-servidor  
- **Acesso em:** 01/08/2020  
- **Disponível em:** [https://youtu.be/SEC-tbKa1KQ](https://youtu.be/SEC-tbKa1KQ)

---

## 1.1 Sistemas e Modelo Cliente-Servidor em Camadas

Um sistema em camadas consiste em uma organização hierárquica em que cada camada fornece serviços à camada hierarquicamente superior a ela.

De acordo com Fielding (2000), embora o sistema em camadas seja considerado um estilo puro, a sua utilização em sistemas baseados em rede se limita a sua combinação com o modelo cliente-servidor a fim de fornecer esse modelo em camadas, sendo os mais comuns as arquiteturas em duas camadas e três camadas.

### Vantagens

- Redução do acoplamento entre as diferentes camadas, ocultando as camadas mais internas das camadas exteriores, exceto a camada adjacente a ela.
- Melhora a capacidade de evolução e reutilização.

### Exemplo

O processamento de protocolos de comunicação em camadas, como as pilhas de protocolos TCP/IP e OSI.

> **Atenção:** A principal desvantagem de se empregar um sistema em camadas se deve à sobrecarga e latência ao processamento de dados, o que é responsável por reduzir o desempenho percebido pelo usuário.

### Componentes Adicionais

A arquitetura cliente-servidor em camadas adiciona componentes de proxy e gateway ao modelo cliente-servidor.  
- **Proxy:** atua como um servidor compartilhado para um ou mais componentes do cliente, recebendo solicitações e encaminhando-as para os componentes do servidor.
- **Gateway:** parece ser um servidor normal para clientes ou proxies que solicitam seus serviços, mas na verdade encaminha essas solicitações para servidores de camada interna.

Esses componentes mediadores adicionais podem ser acrescentados em inúmeras camadas com a finalidade de adicionar recursos tais como balanceamento, verificação de segurança, entre outros.

No contexto da ciência da computação, a arquitetura cliente-servidor é conhecida por possuir duas, três ou várias camadas. O modelo em duas camadas é o mais simples desse tipo de arquitetura.

---

## Arquitetura Cliente-Servidor

A arquitetura cliente-servidor propõe distribuir as tarefas e cargas de trabalho entre aqueles responsáveis por fornecer recursos e serviços (**servidores**) e aqueles que fazem a requisição de serviços (**clientes**).

Em geral, os clientes e servidores se comunicam por meio de uma rede de computadores distintos.

### Cliente

- Realiza requisições para os servidores, aguarda suas respostas e as recebe.
- Pode se conectar a mais de um servidor por vez, interagindo com eles por meio de seu software de aplicação específico.

### Servidor

- Atua apenas após ser solicitado, sempre esperando por um pedido de um cliente, atendendo-os e respondendo.
- Pode se conectar com outros servidores, que também fornecem recursos de rede e interagem com usuários finais.

### Vantagens

- Permite distribuir responsabilidades entre computadores independentes conectados em rede.
- Facilita a manutenção: cada lado pode ser reparado, atualizado ou realocado sem afetar a arquitetura.
- Segurança: dados ficam no servidor, que possui controles de segurança mais robustos.

### Desvantagens

- Os clientes apenas podem solicitar serviços aos servidores.
- Caso o servidor receba muitas solicitações simultâneas, pode ocorrer sobrecarga.
- O modelo não possui a robustez de uma rede baseada em P2P.

Segundo Andrews (1991), o modelo cliente-servidor em camadas também consiste em uma solução para gerenciar a identidade em um sistema distribuído em grande escala, uma vez que o conhecimento por completo de todos os servidores seria proibitivamente caro. Nos servidores com arquitetura em camadas, os serviços são geridos por intermediários e não diretamente por cada cliente.

#### Vídeo recomendado

- **Título:** Arquitetura cliente-servidor em diferentes camadas  
- **Conteúdo:** Explicação detalhada das arquiteturas em duas, três e múltiplas camadas.

---

## Aprofunde-se: Arquitetura Cliente-Servidor em Diferentes Camadas

Para aprofundar seus conhecimentos sobre a arquitetura cliente-servidor em diferentes camadas, é recomendado assistir ao material em vídeo que detalha as arquiteturas em duas, três e múltiplas camadas. Esse conteúdo apresenta exemplos práticos e explica como cada camada pode ser responsável por diferentes funções, como apresentação, lógica de negócio e acesso a dados. Compreender essas variações é fundamental para projetar sistemas escaláveis, seguros e de fácil manutenção, além de ajudar a escolher a melhor abordagem para cada cenário de desenvolvimento.

**Sugestão de vídeo:**  
- Título: Arquitetura cliente-servidor em diferentes camadas  
- Conteúdo: Explicação detalhada das arquiteturas em duas, três e múltiplas camadas, com exemplos práticos e vantagens de cada modelo.

---

# 2. Fluxos de Solicitação, Endpoints e Dependências

O modo mais usual no qual um sistema de tecnologia da informação integra seus produtos aos usuários é por meio de uma **API** (Application Programming Interface), pois são elas que possibilitam a troca de informação entre diferentes serviços, bem como integram serviços, empresas e parceiros.

### Integração e Equipe

- Integração bem-sucedida depende do esforço de toda a equipe de desenvolvimento, responsável por integrar diferentes sistemas.

### Documentação de API

- A documentação correta é indispensável para o sucesso da integração.
- Permite que qualquer membro da equipe implemente soluções rapidamente, mesmo sem conhecimento integral da aplicação.

### Foco da Documentação

- Deve detalhar recursos e endpoints disponíveis.
- Endpoints são os pontos de acesso (URIs) para os recursos da API.

#### Aprofunde-se

- **Artigo:** [Request endpoints](https://cloud.google.com/storage/docs/request-endpoints?hl=pt-br#console)

### Descrições Essenciais em uma API

- Descrever a funcionalidade provida.
- Listar parâmetros de entrada (obrigatórios, opcionais e tipos).
- Documentar o formato de resposta (JSON, XML, etc).
- Especificar métodos HTTP aceitos pelos endpoints.
- Descrever possíveis valores de retorno (sucesso e erro).

#### Boas Práticas e Ferramentas

- Boas práticas de documentação trazem agilidade ao desenvolvimento.
- Existem diversas soluções tecnológicas para auxiliar nesse processo.

- **Artigo:** [Boas práticas em documentação de uma API](https://cloud.google.com/apis/design/naming_convention?hl=pt)

#### Ferramentas Tecnológicas

O material em vídeo a seguir apresenta algumas das principais ferramentas tecnológicas para documentação de APIs.

---

# 3. Documentando as Rotas

Para compreender a importância do tema abordado nesse tópico, é necessário revisitar as definições que compõem a arquitetura REST.

Segundo Fielding (2000), o estilo **Representational State Transfer (REST)** consiste em uma abstração dos elementos que compõem um sistema de hipermídia distribuído. REST ignora detalhes de implementação e foca nos papéis dos componentes, restrições de interação e interpretação de dados.

## Estilos Norteadores da Arquitetura REST

- **Cliente-servidor:** separa interface do usuário do armazenamento de dados, melhorando portabilidade, escalabilidade e simplificando o servidor.
- **Sem estado:** cada solicitação do cliente deve conter todas as informações necessárias; o servidor não armazena contexto.
- **Armazenamento em cache:** respostas podem ser rotuladas para cache, melhorando eficiência da rede.
- **Interface uniforme:** todos os componentes interagem por uma interface padronizada, facilitando evolução, mas pode limitar necessidades específicas.
- **Sistema em camadas:** a arquitetura pode ser composta por camadas hierárquicas, promovendo independência e limitando a complexidade.

#### Vídeo recomendado

- **Título:** Entendendo o REST  
- **Acesso em:** 03/08/2020  
- **Disponível em:** [https://youtu.be/7Cbd8WBGrq4](https://youtu.be/7Cbd8WBGrq4)

---

## Verbos HTTP e Interface Uniforme

> **Reflita:** Roy Fielding, criador do REST, não recomendou métodos específicos, apenas enfatizou a importância de uma interface uniforme.

### Principais Métodos HTTP

| Método | Descrição |
|--------|-----------|
| **GET**    | Solicita uma representação do recurso especificado. Apenas recupera dados. |
| **HEAD**   | Solicita uma resposta idêntica ao GET, mas sem o corpo da resposta. Útil para metainformações. |
| **POST**   | Solicita que o servidor aceite a entidade incluída como um novo subordinado do recurso identificado pelo URI. |
| **PUT**    | Solicita que a entidade seja armazenada no URI fornecido. Modifica ou cria o recurso. |
| **DELETE** | Exclui o recurso especificado. |
| **TRACE**  | Ecoa a solicitação recebida para diagnóstico de alterações feitas por intermediários. |

### Exemplos de Utilização dos Métodos HTTP

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | http://localhost.8080/contacts | Retorna uma lista com todos os contatos. |
| GET | http://localhost.8080/contacts/10 | Retorna o contato de ID 10 ou erro 404 caso não seja encontrado. |
| GET | http://localhost.8080/contacts/paulo | Retorna contatos com “Paulo” no nome. |
| POST | http://localhost.8080/contacts | Adiciona novo contato (dados no corpo em JSON). |
| PUT | http://localhost.8080/contacts/3 | Atualiza o contato de ID 3 (dados no corpo em JSON). |
| DELETE | http://localhost.8080/contacts/15 | Apaga o contato ID 15. |

---

# Considerações Finais

Nesta unidade, você aprendeu a importância da arquitetura cliente-servidor para o desenvolvimento de APIs baseadas na arquitetura REST. Além disso, entendeu que essa arquitetura favorece o desenvolvimento independente de cada um dos lados na arquitetura cliente-servidor. Também compreendeu a importância da documentação no desenvolvimento de uma API, além dos métodos HTTP, que o auxiliarão em suas construções.

---

## O que é Middleware?

**Middleware** é um software intermediário que atua entre diferentes sistemas, aplicações ou camadas de uma arquitetura, facilitando a comunicação, integração e gerenciamento de dados entre eles. Em aplicações web, o middleware pode ser responsável por tarefas como autenticação, registro de logs, tratamento de erros, roteamento de requisições e transformação de dados, sem que o desenvolvedor precise implementar essas funcionalidades diretamente em cada componente da aplicação.

Exemplo: Em uma API, um middleware pode interceptar uma requisição HTTP para verificar se o usuário está autenticado antes de permitir o acesso ao recurso solicitado.
