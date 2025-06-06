package IntroducaoJava.ProgramacaoFuncional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Lambdas {

    public static void main(String[] args) {
        // Exemplo de lambda para imprimir cada item da lista
        List<String> lista = Arrays.asList("A", "B", "C", "D");
        Consumer<String> consumidor = (String s) -> System.out.println(s);
        lista.forEach(consumidor);

        // Exemplo de lambda para calcular a soma de dois números
        OperacaoMatematica soma = (a, b) -> a + b;
        System.out.println("Soma: " + soma.operar(5, 3));

        // Exemplo de lambda para calcular a multiplicação de dois números
        OperacaoMatematica multiplicacao = (a, b) -> a * b;
        System.out.println("Multiplicação: " + multiplicacao.operar(5, 3));
    }

    @FunctionalInterface
    interface OperacaoMatematica {

        int operar(int a, int b);
    }
}
