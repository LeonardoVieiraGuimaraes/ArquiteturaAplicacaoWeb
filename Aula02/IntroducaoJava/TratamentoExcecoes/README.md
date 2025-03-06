# Aula 02 - Estruturas de Dados e Tratamento de Exceções em Java

## Tratamento de Exceções

### Exceções em Java
- **try-catch-finally**: Bloco usado para capturar e tratar exceções e executar código independentemente de uma exceção ocorrer ou não.
  ```java
  try {
      // código que pode lançar uma exceção
  } catch (TipoDeExcecao e) {
      // código para tratar a exceção
  } finally {
      // código que será executado sempre, independentemente de uma exceção ocorrer ou não
  }
  ```
- **Hierarquia de Exceções**: `Exception` é a superclasse de todas as exceções verificadas, enquanto `RuntimeException` é a superclasse de exceções não verificadas.

  ![Hierarquia de Exceções](https://example.com/hierarquia-excecoes.png)

  ```java
  // Exemplo de hierarquia de exceções
  try {
      // código que pode lançar uma exceção
  } catch (RuntimeException e) {
      // código para tratar exceções de tempo de execução
  } catch (Exception e) {
      // código para tratar exceções verificadas
  }
  ```

  A hierarquia de exceções em Java pode ser representada da seguinte forma:

  - `Throwable`
    - `Error`
      - `OutOfMemoryError`
      - `StackOverflowError`
      - ...
    - `Exception`
      - `RuntimeException` (Unchecked Exceptions)
        - `NullPointerException`
        - `ArithmeticException`
        - `ArrayIndexOutOfBoundsException`
        - ...
      - Outras Exceções (Checked Exceptions)
        - `IOException`
        - `SQLException`
        - `ClassNotFoundException`
        - ...

  Exemplo de código para ilustrar a hierarquia:
  ```java
  public class ExemploHierarquiaExcecoes {
      public static void main(String[] args) {
          try {
              metodoQueLancaExcecao();
          } catch (RuntimeException e) {
              System.out.println("Capturada RuntimeException");
          } catch (Exception e) {
              System.out.println("Capturada Exception");
          }
      }

      public static void metodoQueLancaExcecao() throws Exception {
          throw new Exception("Exceção lançada");
      }
  }
  ```
### Diferença Entre Exception e RuntimeException

| Característica            | Exception (Checked)                      | RuntimeException (Unchecked)           |
|---------------------------|------------------------------------------|----------------------------------------|
| Tratamento Obrigatório    | Sim, o compilador exige tratamento.      | Não, o tratamento é opcional.          |
| Causa Comum               | Erros externos (ex: arquivo não encontrado). | Erros de programação (ex: divisão por zero). |
| Herança                   | Herda diretamente de Exception.          | Herda de RuntimeException.             |
| Exemplos                  | IOException, SQLException.               | NullPointerException, ArithmeticException. |

- **Exceções Personalizadas**: Criação de exceções específicas para a aplicação.
  ```java
  // Definindo uma exceção personalizada
  public class MinhaExcecao extends Exception {
      public MinhaExcecao(String mensagem) {
          super(mensagem);
      }
  }

  // Usando a exceção personalizada
  try {
      throw new MinhaExcecao("Ocorreu um erro personalizado");
  } catch (MinhaExcecao e) {
      // código para tratar a exceção personalizada
  }
  ```


### Tratamento de Exceções
Os exemplos de código podem ser encontrados no arquivo `ExceptionHandlingExamples.java` na pasta `IntroducaoJava/TratamentoExcecoes`.


