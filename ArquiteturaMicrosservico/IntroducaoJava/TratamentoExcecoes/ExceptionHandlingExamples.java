package IntroducaoJava.TratamentoExcecoes;

/**
 * O código abaixo demonstra o uso de tratamento de exceções em Java.
 */
public class ExceptionHandlingExamples {

    public static void main(String[] args) {
        // Exemplo de tratamento de exceção com try-catch-finally
        try {
            int result = divide(10, 0); // Tenta dividir por zero
            System.out.println("Resultado: " + result);
        } catch (ArithmeticException e) {
            System.out.println("Erro: Divisão por zero não é permitida."); // Captura e trata a exceção
        } finally {
            System.out.println("Bloco finally executado."); // Executa o bloco finally independentemente de uma exceção ocorrer ou não
        }

        // Exemplo de tratamento de múltiplas exceções
        try {
            int[] array = new int[5];
            array[10] = 50; // Tenta acessar um índice fora dos limites do array
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Erro: Índice do array fora dos limites."); // Captura e trata a exceção
        } catch (Exception e) {
            System.out.println("Erro: Ocorreu uma exceção."); // Captura e trata qualquer outra exceção
        }

        // Exemplo de hierarquia de exceções
        try {
            throw new CustomException("Exceção personalizada lançada"); // Lança uma exceção personalizada
        } catch (CustomException e) {
            System.out.println("Erro: " + e.getMessage()); // Captura e trata a exceção personalizada
        } catch (Exception e) {
            System.out.println("Erro: Ocorreu uma exceção."); // Captura e trata qualquer outra exceção
        }
    }

    /**
     * Método que realiza a divisão de dois números inteiros.
     *
     * @param a Dividendo
     * @param b Divisor
     * @return Resultado da divisão
     * @throws ArithmeticException Se o divisor for zero
     */
    public static int divide(int a, int b) throws ArithmeticException {
        return a / b; // Pode lançar ArithmeticException se b for zero
    }
}

/**
 * Classe que representa uma exceção personalizada.
 */
class CustomException extends Exception {

    public CustomException(String message) {
        super(message); // Chama o construtor da superclasse Exception
    }
}
