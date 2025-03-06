package IntroducaoJava.ProgramacaoOrientadoObjetos;

// Classe Calculadora para demonstrar polimorfismo por sobrecarga
public class Calculadora {

    // Método sobrecarregado para somar dois inteiros
    public int somar(int a, int b) {
        return a + b;
    }

    // Método sobrecarregado para somar dois doubles
    public double somar(double a, double b) {
        return a + b;
    }

    // Método sobrecarregado para somar três inteiros
    public int somar(int a, int b, int c) {
        return a + b + c;
    }

    public static void main(String[] args) {
        Calculadora calc = new Calculadora();
        System.out.println("Soma de 2 inteiros: " + calc.somar(1, 2));
        System.out.println("Soma de 2 doubles: " + calc.somar(1.5, 2.5));
        System.out.println("Soma de 3 inteiros: " + calc.somar(1, 2, 3));
    }
}
