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


        // Array tridimensional: 3 dias, 2 horários, 3 regiões
        double[][][] temperaturas = new double[3][2][3];

        // Atribuindo valores
        // Dia 1
        temperaturas[0][0][0] = 25.5; // Dia 1, Manhã, Região 1
        temperaturas[0][0][1] = 26.0; // Dia 1, Manhã, Região 2
        temperaturas[0][0][2] = 24.8; // Dia 1, Manhã, Região 3
        temperaturas[0][1][0] = 30.0; // Dia 1, Tarde, Região 1
        temperaturas[0][1][1] = 31.2; // Dia 1, Tarde, Região 2
        temperaturas[0][1][2] = 29.5; // Dia 1, Tarde, Região 3

        // Dia 2
        temperaturas[1][0][0] = 24.0; // Dia 2, Manhã, Região 1
        temperaturas[1][0][1] = 25.5; // Dia 2, Manhã, Região 2
        temperaturas[1][0][2] = 23.8; // Dia 2, Manhã, Região 3
        temperaturas[1][1][0] = 29.0; // Dia 2, Tarde, Região 1
        temperaturas[1][1][1] = 30.5; // Dia 2, Tarde, Região 2
        temperaturas[1][1][2] = 28.0; // Dia 2, Tarde, Região 3

        // Dia 3
        temperaturas[2][0][0] = 26.0; // Dia 3, Manhã, Região 1
        temperaturas[2][0][1] = 27.0; // Dia 3, Manhã, Região 2
        temperaturas[2][0][2] = 25.5; // Dia 3, Manhã, Região 3
        temperaturas[2][1][0] = 31.0; // Dia 3, Tarde, Região 1
        temperaturas[2][1][1] = 32.0; // Dia 3, Tarde, Região 2
        temperaturas[2][1][2] = 30.0; // Dia 3, Tarde, Região 3


         // Acessando e exibindo valores
         for (int dia = 0; dia < temperaturas.length; dia++) {
            System.out.println("Dia " + (dia + 1) + ":");
            for (int horario = 0; horario < temperaturas[dia].length; horario++) {
                System.out.print((horario == 0 ? "Manhã: " : "Tarde: "));
                for (int regiao = 0; regiao < temperaturas[dia][horario].length; regiao++) {
                    System.out.print("Região " + (regiao + 1) + ": " + temperaturas[dia][horario][regiao] + "°C  ");
                }
                System.out.println();
            }
            System.out.println();
        }

    }
}
