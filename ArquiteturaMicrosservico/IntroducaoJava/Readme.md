# Instalação do JDK e configuração do ambiente

1. **Baixar o JDK**:
   - Acesse o site oficial da Oracle: [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
   - Baixe a versão mais recente do JDK compatível com seu sistema operacional.

2. **Instalar o JDK**:
   - Siga as instruções de instalação fornecidas pelo instalador do JDK.

# Primeiro programa

1. **Criar um arquivo Java**:
   - Crie um novo arquivo com o nome `HelloWorld.java`.

2. **Escrever o código**:
   - Abra o arquivo `HelloWorld.java` em um editor de texto e adicione o seguinte código:
     ```java
     public class HelloWorld {
         public static void main(String[] args) {
             System.out.println("Hello, World!");
         }
     }
     ```

3. **Compilar o programa**:
   - Abra o terminal, navegue até o diretório onde o arquivo `HelloWorld.java` está salvo e execute o comando:
     ```sh
     javac HelloWorld.java
     ```

4. **Executar o programa**:
   - Após a compilação, execute o comando:
     ```sh
     java HelloWorld
     ```
   - Você verá a saída `Hello, World!` no terminal.
