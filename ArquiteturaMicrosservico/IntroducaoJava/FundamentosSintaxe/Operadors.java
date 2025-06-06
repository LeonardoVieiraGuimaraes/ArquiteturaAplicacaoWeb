package IntroducaoJava.FundamentosSintaxe;

public class Operadors {

    public static void main(String[] args) {
        // Operadores aritméticos
        int a = 10;
        int b = 5;
        System.out.println("a + b = " + (a + b)); // Adição
        System.out.println("a - b = " + (a - b)); // Subtração
        System.out.println("a * b = " + (a * b)); // Multiplicação
        System.out.println("a / b = " + (a / b)); // Divisão
        System.out.println("a % b = " + (a % b)); // Módulo

        // Operadores relacionais
        System.out.println("a == b: " + (a == b)); // Igual a
        System.out.println("a != b: " + (a != b)); // Diferente de
        System.out.println("a > b: " + (a > b)); // Maior que
        System.out.println("a < b: " + (a < b)); // Menor que
        System.out.println("a >= b: " + (a >= b)); // Maior ou igual a
        System.out.println("a <= b: " + (a <= b)); // Menor ou igual a

        // Operadores lógicos
        boolean x = true;
        boolean y = false;
        System.out.println("x && y: " + (x && y)); // E lógico
        System.out.println("x || y: " + (x || y)); // OU lógico
        System.out.println("!x: " + (!x)); // NÃO lógico

        // Operadores de incremento e decremento
        int c = 10;
        System.out.println("c++: " + (c++)); // Pós-incremento
        System.out.println("++c: " + (++c)); // Pré-incremento
        System.out.println("c--: " + (c--)); // Pós-decremento
        System.out.println("--c: " + (--c)); // Pré-decremento

        // Operador ternário
        int result = (a > b) ? a : b;
        System.out.println("Maior valor entre a e b: " + result);
    }
}
