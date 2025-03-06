# Aula 02 - Manipulação de Arquivos e I/O em Java

## Manipulação de Arquivos e I/O

### Leitura e Escrita de Arquivos
- **File**: Classe usada para representar arquivos e diretórios. Pode ser usada para criar, excluir e inspecionar propriedades de arquivos e diretórios.
- **FileReader**: Classe usada para ler dados de arquivos de texto. Lê caracteres de um arquivo de texto.
- **BufferedReader**: Classe usada para ler texto de um fluxo de entrada de caracteres, bufferizando os caracteres para fornecer uma leitura eficiente. Pode ler linhas inteiras de texto de uma vez.

### Trabalhando com Streams
- **Byte Streams**: Usados para ler e escrever dados binários. As classes principais são `InputStream` e `OutputStream`.
- **Character Streams**: Usados para ler e escrever dados de texto. As classes principais são `Reader` e `Writer`.

### Serialização de Objetos
- **ObjectOutputStream**: Classe usada para gravar objetos em um fluxo de saída. Converte objetos em um formato que pode ser gravado em um arquivo ou transmitido pela rede.
- **ObjectInputStream**: Classe usada para ler objetos de um fluxo de entrada. Converte o formato serializado de volta em um objeto.

## Exemplos de Código

### Leitura e Escrita de Arquivos
Os exemplos de código podem ser encontrados no arquivo `FileIOExamples.java` na pasta `IntroducaoJava/ManipulacaoArquivos`.

### Trabalhando com Streams
Os exemplos de código podem ser encontrados no arquivo `StreamExamples.java` na pasta `IntroducaoJava/ManipulacaoArquivos`.

### Serialização de Objetos
Os exemplos de código podem ser encontrados no arquivo `SerializationExamples.java` na pasta `IntroducaoJava/ManipulacaoArquivos`.
