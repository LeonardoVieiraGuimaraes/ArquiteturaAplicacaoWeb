package IntroducaoJava.EstruturaDados;

import java.util.*;

public class CollectionExamples {

    public static void main(String[] args) {
        // Exemplo de List (ArrayList e LinkedList)
        List<String> arrayList = new ArrayList<>();
        arrayList.add("A"); // Adiciona "A" ao ArrayList
        arrayList.add("B"); // Adiciona "B" ao ArrayList
        arrayList.add("C"); // Adiciona "C" ao ArrayList
        for (String s : arrayList) {
            System.out.println(s); // Imprime cada elemento do ArrayList
        }

        List<String> linkedList = new LinkedList<>();
        linkedList.add("X"); // Adiciona "X" ao LinkedList
        linkedList.add("Y"); // Adiciona "Y" ao LinkedList
        linkedList.add("Z"); // Adiciona "Z" ao LinkedList
        for (String s : linkedList) {
            System.out.println(s); // Imprime cada elemento do LinkedList
        }

        // Exemplo de Set (HashSet e TreeSet)
        Set<Integer> hashSet = new HashSet<>();
        hashSet.add(1); // Adiciona 1 ao HashSet
        hashSet.add(2); // Adiciona 2 ao HashSet
        hashSet.add(3); // Adiciona 3 ao HashSet
        for (int i : hashSet) {
            System.out.println(i); // Imprime cada elemento do HashSet
        }

        Set<Integer> treeSet = new TreeSet<>();
        treeSet.add(10); // Adiciona 10 ao TreeSet
        treeSet.add(20); // Adiciona 20 ao TreeSet
        treeSet.add(30); // Adiciona 30 ao TreeSet
        for (int i : treeSet) {
            System.out.println(i); // Imprime cada elemento do TreeSet
        }

        // Exemplo de Map (HashMap e TreeMap)
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("One", 1); // Adiciona "One" com valor 1 ao HashMap
        hashMap.put("Two", 2); // Adiciona "Two" com valor 2 ao HashMap
        hashMap.put("Three", 3); // Adiciona "Three" com valor 3 ao HashMap
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue()); // Imprime cada par chave-valor do HashMap
        }

        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Alpha", 100); // Adiciona "Alpha" com valor 100 ao TreeMap
        treeMap.put("Beta", 200); // Adiciona "Beta" com valor 200 ao TreeMap
        treeMap.put("Gamma", 300); // Adiciona "Gamma" com valor 300 ao TreeMap
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue()); // Imprime cada par chave-valor do TreeMap
        }

        // Exemplo de Queue
        Queue<String> queue = new LinkedList<>();
        queue.add("First"); // Adiciona "First" à Queue
        queue.add("Second"); // Adiciona "Second" à Queue
        queue.add("Third"); // Adiciona "Third" à Queue
        while (!queue.isEmpty()) {
            System.out.println(queue.poll()); // Remove e imprime cada elemento da Queue
        }

        // Exemplo de Deque
        Deque<String> deque = new LinkedList<>();
        deque.addFirst("Start"); // Adiciona "Start" ao início do Deque
        deque.addLast("End"); // Adiciona "End" ao final do Deque
        System.out.println(deque.removeFirst()); // Remove e imprime o primeiro elemento do Deque
        System.out.println(deque.removeLast()); // Remove e imprime o último elemento do Deque
    }
}
