package IntroducaoJava.ProgramacaoOrientadoObjetos;

// Classe Aluno que herda de Pessoa
public class Aluno extends Pessoa {

    private String matricula;

    public Aluno(String nome, int idade, String matricula) {
        super(nome, idade);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public void exibirDados() {
        // A anotação @Override indica que este método está sobrescrevendo um método da superclasse (Pessoa).
        super.exibirDados();
        System.out.println("Matrícula: " + matricula);
    }
}