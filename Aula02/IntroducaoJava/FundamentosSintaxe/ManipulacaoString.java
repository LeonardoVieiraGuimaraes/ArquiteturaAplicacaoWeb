package IntroducaoJava.FundamentosSintaxe;

public class ManipulacaoString {

    public static void main(String[] args) {
        // Declaração e inicialização de strings
        String saudacao = "Olá";
        String nome = "Mundo";

        // Concatenação de strings
        String mensagem = saudacao + ", " + nome + "!";
        System.out.println(mensagem);

        // Métodos de string
        System.out.println("Tamanho da mensagem: " + mensagem.length());
        System.out.println("Mensagem em maiúsculas: " + mensagem.toUpperCase());
        System.out.println("Mensagem em minúsculas: " + mensagem.toLowerCase());
        System.out.println("Primeiro caractere: " + mensagem.charAt(0));
        System.out.println("Substituir 'Mundo' por 'Java': " + mensagem.replace("Mundo", "Java"));

        // Formatação de strings
        int idade = 25;
        String formato = String.format("Meu nome é %s e eu tenho %d anos.", nome, idade);
        System.out.println(formato);
    }
}
