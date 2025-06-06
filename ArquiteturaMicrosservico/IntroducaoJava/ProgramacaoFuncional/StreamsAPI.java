package IntroducaoJava.ProgramacaoFuncional;

import java.util.Arrays;
import java.util.List;

public class StreamsAPI {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Filtrar números pares
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .toList();
        System.out.println("Números pares: " + evenNumbers);

        // Mapear para o quadrado dos números
        List<Integer> squaredNumbers = numbers.stream()
                .map(n -> n * n)
                .toList();
        System.out.println("Quadrado dos números: " + squaredNumbers);

        // Reduzir para a soma dos números
        int sum = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println("Soma dos números: " + sum);
    }
}
