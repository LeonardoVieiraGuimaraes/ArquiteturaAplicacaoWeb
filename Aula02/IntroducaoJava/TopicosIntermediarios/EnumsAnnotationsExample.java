package IntroducaoJava.TopicosIntermediarios;

public class EnumsAnnotationsExample {

    // Enumeração
    public enum DiaDaSemana {
        SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO, DOMINGO
    }

    // Anotação personalizada
    public @interface Informacao {

        String autor();

        String data();

        int versao();
    }

    @Informacao(autor = "João Silva", data = "10/10/2023", versao = 1)
    public static void metodoAnotado() {
        System.out.println("Este método está anotado com @Informacao.");
    }

    public static void main(String[] args) {
        // Usando a enumeração
        DiaDaSemana hoje = DiaDaSemana.QUARTA;
        System.out.println("Hoje é: " + hoje);

        // Chamando o método anotado
        metodoAnotado();
    }
}
