# Aula 02 - Estruturas de Dados em Java

## Estruturas de Dados

### Arrays
- **Unidimensionais**: Arrays que possuem uma única linha de elementos.
  ```java
  int[] array = new int[10];
  // Atribuir valor
  array[0] = 1;
  // Acessar valor
  int value = array[0];
  ```
- **Multidimensionais**: Arrays que possuem múltiplas linhas e colunas de elementos.
  ```java
  int[][] array = new int[10][10];
  // Atribuir valor
  array[0][0] = 1;
  // Acessar valor
  int value = array[0][0];
  ```
- **Tridimensionais**: Arrays que possuem múltiplas linhas, colunas e profundidade de elementos.
  ```java
  int[][][] array = new int[10][10][10];
  // Atribuir valor
  array[0][0][0] = 1;
  // Acessar valor
  int value = array[0][0][0];
  ```

### Coleções do Java
- **List**: Interface que representa uma coleção ordenada de elementos.
  - `ArrayList`: Implementação baseada em array dinâmico.
    ```java
    List<String> arrayList = new ArrayList<>();
    // Adicionar elemento
    arrayList.add("Elemento");
    // Acessar elemento
    String element = arrayList.get(0);
    // Remover elemento
    arrayList.remove(0);
    ```
  - `LinkedList`: Implementação baseada em lista duplamente ligada.
    ```java
    List<String> linkedList = new LinkedList<>();
    // Adicionar elemento
    linkedList.add("Elemento");
    // Acessar elemento
    String element = linkedList.get(0);
    // Remover elemento
    linkedList.remove(0);
    ```

- **Set**: Interface que representa uma coleção que não permite elementos duplicados.
  - `HashSet`: Implementação baseada em tabela hash.
    ```java
    Set<String> hashSet = new HashSet<>();
    // Adicionar elemento
    hashSet.add("Elemento");
    // Verificar se contém elemento
    boolean contains = hashSet.contains("Elemento");
    // Remover elemento
    hashSet.remove("Elemento");
    ```
  - `TreeSet`: Implementação baseada em árvore de busca binária.
    ```java
    Set<String> treeSet = new TreeSet<>();
    // Adicionar elemento
    treeSet.add("Elemento");
    // Verificar se contém elemento
    boolean contains = treeSet.contains("Elemento");
    // Remover elemento
    treeSet.remove("Elemento");
    ```

- **Map**: Interface que representa uma coleção de pares chave-valor.
  - `HashMap`: Implementação baseada em tabela hash.
    ```java
    Map<String, String> hashMap = new HashMap<>();
    // Adicionar par chave-valor
    hashMap.put("Chave", "Valor");
    // Acessar valor
    String value = hashMap.get("Chave");
    // Remover par chave-valor
    hashMap.remove("Chave");
    ```
  - `TreeMap`: Implementação baseada em árvore de busca binária.
    ```java
    Map<String, String> treeMap = new TreeMap<>();
    // Adicionar par chave-valor
    treeMap.put("Chave", "Valor");
    // Acessar valor
    String value = treeMap.get("Chave");
    // Remover par chave-valor
    treeMap.remove("Chave");
    ```

- **Queue**: Interface que representa uma coleção de elementos que segue a ordem FIFO (First-In-First-Out).
  ```java
  Queue<String> queue = new LinkedList<>();
  // Adicionar elemento
  queue.add("Elemento");
  // Acessar e remover elemento
  String element = queue.poll();
  // Acessar elemento sem remover
  String peekElement = queue.peek();
  ```
- **Deque**: Interface que representa uma coleção de elementos que permite inserções e remoções em ambas as extremidades.
  ```java
  Deque<String> deque = new LinkedList<>();
  // Adicionar elemento no início
  deque.addFirst("Elemento");
  // Adicionar elemento no final
  deque.addLast("Elemento");
  // Acessar e remover elemento do início
  String firstElement = deque.pollFirst();
  // Acessar e remover elemento do final
  String lastElement = deque.pollLast();
  // Acessar elemento do início sem remover
  String peekFirstElement = deque.peekFirst();
  // Acessar elemento do final sem remover
  String peekLastElement = deque.peekLast();
  ```

