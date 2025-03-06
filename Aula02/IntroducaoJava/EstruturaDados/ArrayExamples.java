package IntroducaoJava.EstruturaDados;

/**
 * O código abaixo demonstra o uso de arrays unidimensionais e multidimensionais
 * em Java.
 */
public class ArrayExamples {

    public static void main(String[] args) {
        // Exemplo de array unidimensional
        int[] arrayUnidimensional = {1, 2, 3, 4, 5};
        for (int i : arrayUnidimensional) {
            System.out.println(i); // Imprime cada elemento do array unidimensional
        }

        // Exemplo de array multidimensional
        int[][] arrayMultidimensional = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        for (int i = 0; i < arrayMultidimensional.length; i++) {
            for (int j = 0; j < arrayMultidimensional[i].length; j++) {
                System.out.print(arrayMultidimensional[i][j] + " "); // Imprime cada elemento do array multidimensional
            }
            System.out.println(); // Nova linha após cada linha do array multidimensional
        }
    }
}
