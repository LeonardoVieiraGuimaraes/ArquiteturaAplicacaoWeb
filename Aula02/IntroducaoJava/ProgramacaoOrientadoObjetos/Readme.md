# Programação Orientada a Objetos (POO)

## Tópicos

- Classes, objetos, métodos e construtores.
- Pilares da POO:
  - Encapsulamento (getters/setters).
    - Encapsulamento é o conceito de esconder os detalhes internos de um objeto e expor apenas o que é necessário através de métodos públicos. Isso é feito usando getters e setters para acessar e modificar os atributos privados de uma classe.
    - **Exemplo de sintaxe:**
      ```java
      public class Pessoa {
          private String nome;

          public String getNome() {
              return nome;
          }

          public void setNome(String nome) {
              this.nome = nome;
          }
      }
      ```
  - Herança (`extends`).
    - Herança é o mecanismo pelo qual uma classe pode herdar atributos e métodos de outra classe. A classe que herda é chamada de subclasse, e a classe da qual ela herda é chamada de superclasse. Isso promove a reutilização de código.
    - **Exemplo de sintaxe:**
      ```java
      public class Animal {
          public void comer() {
              System.out.println("Animal comendo");
          }
      }

      public class Cachorro extends Animal {
          public void latir() {
              System.out.println("Cachorro latindo");
          }
      }
      ```
  - Polimorfismo (sobrecarga e sobrescrita).
    - Polimorfismo permite que objetos de diferentes classes sejam tratados como objetos de uma classe comum. Existem dois tipos principais de polimorfismo: sobrecarga (métodos com o mesmo nome, mas diferentes assinaturas) e sobrescrita (métodos da subclasse que substituem métodos da superclasse).
    - **Exemplo de sintaxe (sobrecarga):**
      ```java
      public class Calculadora {
          public int somar(int a, int b) {
              return a + b;
          }

          public double somar(double a, double b) {
              return a + b;
          }
      }
      ```
    - **Exemplo de sintaxe (sobrescrita):**
      ```java
      public class Animal {
          public void fazerSom() {
              System.out.println("Animal fazendo som");
          }
      }

      public class Cachorro extends Animal {
          @Override
          public void fazerSom() {
              System.out.println("Cachorro latindo");
          }
      }
      ```
  - Abstração (classes abstratas e interfaces).
    - Abstração é o processo de esconder os detalhes complexos de implementação e mostrar apenas a funcionalidade essencial. Isso é alcançado usando classes abstratas e interfaces, que definem métodos que devem ser implementados pelas classes concretas.
    - **Exemplo de sintaxe (classe abstrata):**
      ```java
      public abstract class Animal {
          public abstract void fazerSom();
      }

      public class Cachorro extends Animal {
          @Override
          public void fazerSom() {
              System.out.println("Cachorro latindo");
          }
      }
      ```
    - **Exemplo de sintaxe (interface):**
      ```java
      public interface Animal {
          void fazerSom();
      }

      public class Cachorro implements Animal {
          @Override
          public void fazerSom() {
              System.out.println("Cachorro latindo");
          }
      }
      ```
