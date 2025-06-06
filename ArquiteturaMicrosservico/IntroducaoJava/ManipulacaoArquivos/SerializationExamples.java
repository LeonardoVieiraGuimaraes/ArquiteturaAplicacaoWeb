package IntroducaoJava.ManipulacaoArquivos;

import java.io.*;

/**
 * O código abaixo demonstra a serialização e desserialização de objetos em
 * Java.
 */
public class SerializationExamples {

    public static void main(String[] args) {
        // Exemplo de serialização de objeto
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.ser"))) {
            Person person = new Person("João", 30);
            oos.writeObject(person);
        } catch (IOException e) {
            System.out.println("Erro ao serializar o objeto: " + e.getMessage());
        }

        // Exemplo de desserialização de objeto
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.ser"))) {
            Person person = (Person) ois.readObject();
            System.out.println("Nome: " + person.getName() + ", Idade: " + person.getAge());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar o objeto: " + e.getMessage());
        }
    }
}

/**
 * Classe que representa uma pessoa e implementa Serializable.
 */
class Person implements Serializable {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
