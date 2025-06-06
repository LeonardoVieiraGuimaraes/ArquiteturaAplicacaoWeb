package IntroducaoJava.ProgramacaoOrientadoObjetos;

// Classe base Pessoa
public class Pessoa {

    // Atributos encapsulados
    private String nome;
    private int idade;

    // Construtor
    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    // Getters e setters para encapsulamento
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    // Método para exibir dados
    public void exibirDados() {
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
    }
}
