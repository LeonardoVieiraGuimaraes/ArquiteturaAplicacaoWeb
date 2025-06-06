package IntroducaoJava.TopicosIntermediarios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class JavaTimeExample {

    public static void main(String[] args) {
        // Obtendo a data atual
        LocalDate dataAtual = LocalDate.now();
        System.out.println("Data atual: " + dataAtual);

        // Obtendo a hora atual
        LocalTime horaAtual = LocalTime.now();
        System.out.println("Hora atual: " + horaAtual);

        // Obtendo a data e hora atuais
        LocalDateTime dataHoraAtual = LocalDateTime.now();
        System.out.println("Data e hora atuais: " + dataHoraAtual);

        // Formatando a data e hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataHoraFormatada = dataHoraAtual.format(formatter);
        System.out.println("Data e hora formatadas: " + dataHoraFormatada);
    }
}
