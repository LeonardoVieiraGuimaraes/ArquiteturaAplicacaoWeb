package IntroducaoJava.ProgramacaoOrientadoObjetos;

// Classe Professor que herda de Pessoa
public class Professor extends Pessoa {

    private String disciplina;

    public Professor(String nome, int idade, String disciplina) {
        super(nome, idade);
        this.disciplina = disciplina;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    public void exibirDados() {
        super.exibirDados();
        System.out.println("Disciplina: " + disciplina);
    }
}
