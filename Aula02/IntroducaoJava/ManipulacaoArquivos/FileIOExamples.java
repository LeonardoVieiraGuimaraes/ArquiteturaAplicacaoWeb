package IntroducaoJava.ManipulacaoArquivos;

import java.io.*;

/**
 * O código abaixo demonstra a leitura e escrita de arquivos em Java.
 */
public class FileIOExamples {

    public static void main(String[] args) {
        // Exemplo de escrita em arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("example.txt"))) {
            writer.write("Olá, mundo!");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }

        // Exemplo de leitura de arquivo
        try (BufferedReader reader = new BufferedReader(new FileReader("example.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
