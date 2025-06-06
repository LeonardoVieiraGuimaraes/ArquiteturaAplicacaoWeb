package IntroducaoJava.FundamentosSintaxe;

public class EstruturasControle {

    public static void main(String[] args) {
        // Estrutura de controle if-else
        int numero = 10;
        if (numero > 0) {
            System.out.println("O número é positivo.");
        } else {
            System.out.println("O número é negativo ou zero.");
        }

        // Estrutura de controle switch
        int diaDaSemana = 3;
        switch (diaDaSemana) {
            case 1:
                System.out.println("Domingo");
                break;
            case 2:
                System.out.println("Segunda-feira");
                break;
            case 3:
                System.out.println("Terça-feira");
                break;
            case 4:
                System.out.println("Quarta-feira");
                break;
            case 5:
                System.out.println("Quinta-feira");
                break;
            case 6:
                System.out.println("Sexta-feira");
                break;
            case 7:
                System.out.println("Sábado");
                break;
            default:
                System.out.println("Dia inválido");
                break;
        }

        // Estrutura de controle for
        for (int i = 0; i < 5; i++) {
            System.out.println("Valor de i: " + i);
        }

        // Estrutura de controle while
        int contador = 0;
        while (contador < 5) {
            System.out.println("Contador: " + contador);
            contador++;
        }

        // Estrutura de controle do-while
        int contadorDoWhile = 0;
        do {
            System.out.println("Contador do-while: " + contadorDoWhile);
            contadorDoWhile++;
        } while (contadorDoWhile < 5);
    }
}
