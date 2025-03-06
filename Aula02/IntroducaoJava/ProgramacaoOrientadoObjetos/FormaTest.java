package IntroducaoJava.ProgramacaoOrientadoObjetos;

// Classe abstrata Forma para demonstrar abstração
abstract class Forma {

    public abstract double calcularArea();
}

// Interface Desenhavel para demonstrar abstração
interface Desenhavel {

    void desenhar();
}

// Classe Circulo que implementa Desenhavel e herda de Forma
class Circulo extends Forma implements Desenhavel {

    private double raio;

    public Circulo(double raio) {
        this.raio = raio;
    }

    @Override
    public double calcularArea() {
        return Math.PI * raio * raio;
    }

    @Override
    public void desenhar() {
        System.out.println("Desenhando um círculo.");
    }
}

// Classe Retangulo que implementa Desenhavel e herda de Forma
class Retangulo extends Forma implements Desenhavel {

    // As variáveis largura e altura são declaradas como final para garantir que seus valores não sejam alterados após a inicialização
    private final double largura;
    private final double altura;

    public Retangulo(double largura, double altura) {
        this.largura = largura;
        this.altura = altura;
    }

    @Override
    public double calcularArea() {
        return largura * altura;
    }

    @Override
    public void desenhar() {
        System.out.println("Desenhando um retângulo.");
    }
}

// Classe de teste para formas
public class FormaTest {

    public static void main(String[] args) {
        Circulo circulo = new Circulo(5);
        Retangulo retangulo = new Retangulo(4, 6);

        System.out.println("Área do círculo: " + circulo.calcularArea());
        circulo.desenhar();

        System.out.println("Área do retângulo: " + retangulo.calcularArea());
        retangulo.desenhar();
    }
}
