package com.example.tarefa.service;

import java.util.List;

public record ListTask(List<ListItem> Itens,
                      int page,
                      int pageSize,
                      int totalPages,
                      long totalElements) {
}