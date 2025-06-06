package IntroducaoJava.TopicosIntermediarios;

public class GenericsExample {

    // Classe genérica
    public static class Caixa<T> {

        private T conteudo;

        public void setConteudo(T conteudo) {
            this.conteudo = conteudo;
        }

        public T getConteudo() {
            return conteudo;
        }
    }

    // Método genérico
    public static <T> void imprimirArray(T[] array) {
        for (T elemento : array) {
            System.out.print(elemento + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Usando a classe genérica
        Caixa<Integer> caixaInteiro = new Caixa<>();
        caixaInteiro.setConteudo(123);
        System.out.println("Conteúdo da caixa: " + caixaInteiro.getConteudo());

        // Usando o método genérico
        Integer[] numeros = {1, 2, 3, 4, 5};
        imprimirArray(numeros);
    }
}
