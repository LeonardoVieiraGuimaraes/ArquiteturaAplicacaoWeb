package IntroducaoJava.ProgramacaoOrientadoObjetos;

public class Executavel {

    public static void main(String[] args) {
        // Configurando a codificação para UTF-8
        // System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        // Criando instâncias de Professor e Aluno
        Professor professor = new Professor("Dr. Silva", 45, "Matemática");
        Aluno aluno = new Aluno("João", 20, "2021001");

        // Exibindo dados do professor
        System.out.println("Dados do Professor:");
        professor.exibirDados();

        // Exibindo dados do aluno
        System.out.println("\nDados do Aluno:");
        aluno.exibirDados();
    }
}
