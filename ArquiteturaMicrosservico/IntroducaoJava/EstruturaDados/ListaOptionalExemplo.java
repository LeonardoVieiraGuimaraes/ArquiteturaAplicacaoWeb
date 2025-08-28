import java.util.Optional; // Importa a classe Optional para manipulação de valores opcionais
import java.util.List;     // Importa a interface List para listas
import java.util.ArrayList; // Importa a implementação ArrayList

// Exemplo de uso de Optional em uma lista
public class ListaOptionalExemplo {
    public static void main(String[] args) {
        // Cria uma lista de Optional<String>
        List<Optional<String>> lista = new ArrayList<>();

        // Adiciona um valor presente
        lista.add(Optional.of("Primeiro"));
        // Adiciona um valor ausente (Optional vazio)
        lista.add(Optional.empty());
        // Adiciona outro valor presente
        lista.add(Optional.of("Segundo"));

        // Percorre a lista e exibe o valor se presente, ou uma mensagem padrão se ausente
        for (Optional<String> item : lista) {
            // orElse retorna o valor se presente, ou "Valor ausente" se estiver vazio
            System.out.println(item.orElse("Valor ausente"));
        }

        // Exemplo de uso de isPresent()
        System.out.println("\nExemplo de isPresent():");
        for (Optional<String> item : lista) {
            if (item.isPresent()) {
                System.out.println("Valor presente: " + item.get());
            } else {
                System.out.println("Valor ausente");
            }
        }

        // Exemplo de uso de ifPresent()
        System.out.println("\nExemplo de ifPresent():");
        for (Optional<String> item : lista) {
            item.ifPresent(valor -> System.out.println("Valor presente: " + valor));
        }

        // Exemplo de uso de orElseGet()
        System.out.println("\nExemplo de orElseGet():");
        for (Optional<String> item : lista) {
            System.out.println(item.orElseGet(() -> "Valor padrão gerado"));
        }

        // Exemplo de uso de orElseThrow()
        System.out.println("\nExemplo de orElseThrow():");
        for (Optional<String> item : lista) {
            try {
                // Só lança exceção se estiver vazio
                System.out.println(item.orElseThrow(() -> new RuntimeException("Valor ausente!")));
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}