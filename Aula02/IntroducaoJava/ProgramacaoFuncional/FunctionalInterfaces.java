package IntroducaoJava.ProgramacaoFuncional;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalInterfaces {

    public static void main(String[] args) {
        // Exemplo de Predicate
        Predicate<Integer> isEven = number -> number % 2 == 0;
        System.out.println("10 é par? " + isEven.test(10)); // true
        System.out.println("7 é par? " + isEven.test(7)); // false

        // Exemplo de Function
        Function<String, Integer> stringLength = str -> str.length();
        System.out.println("Comprimento da string 'Olá': " + stringLength.apply("Olá")); // 3

        // Exemplo de Consumer
        Consumer<String> printUpperCase = str -> System.out.println(str.toUpperCase());
        printUpperCase.accept("olá mundo"); // OLÁ MUNDO
    }
}
