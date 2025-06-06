# Concorrência e Multithreading

## Tópicos

- Threads: criação e gerenciamento.
  - Threads são unidades básicas de execução em um programa. Elas podem ser criadas estendendo a classe `Thread` ou implementando a interface `Runnable`. O gerenciamento de threads inclui iniciar, pausar, retomar e interromper threads.

- `Runnable` vs `Thread`.
  - `Runnable` é uma interface funcional que deve ser implementada por qualquer classe cujas instâncias são destinadas a serem executadas por uma thread. `Thread` é uma classe que representa uma thread de execução. A principal diferença é que ao usar `Runnable`, a classe pode estender outra classe, enquanto ao estender `Thread`, isso não é possível.

- Sincronização (`synchronized`, `Lock`).
  - Sincronização é o processo de controlar o acesso a recursos compartilhados por múltiplas threads para evitar condições de corrida. O bloco `synchronized` e a palavra-chave `synchronized` são usados para garantir que apenas uma thread possa acessar um recurso de cada vez. A interface `Lock` oferece um controle mais flexível e sofisticado sobre o acesso a recursos compartilhados.

- Executors e thread pools.
  - Executors são uma maneira de gerenciar a criação e execução de threads. Um thread pool é um grupo de threads reutilizáveis que são gerenciadas por um executor. Isso ajuda a melhorar o desempenho e a utilização dos recursos do sistema, reutilizando threads existentes em vez de criar novas para cada tarefa.
