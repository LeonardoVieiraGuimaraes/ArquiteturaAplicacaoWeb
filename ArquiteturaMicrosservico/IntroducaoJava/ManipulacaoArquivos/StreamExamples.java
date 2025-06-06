package IntroducaoJava.ManipulacaoArquivos;

import java.io.*;

/**
 * O código abaixo demonstra o uso de byte streams e character streams em Java.
 */
public class StreamExamples {

    public static void main(String[] args) {
        // Exemplo de byte stream
        try (FileOutputStream fos = new FileOutputStream("byteStreamExample.txt")) {
            fos.write("Exemplo de byte stream".getBytes());
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }

        // Exemplo de character stream
        try (FileWriter fw = new FileWriter("charStreamExample.txt")) {
            fw.write("Exemplo de character stream");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
}
