package com.cadastro.tarefas.dto;

import java.util.List;

public record TaskDto(List<TaskItemDto> feedItens,
                      int page,
                      int pageSize,
                      int totalPages,
                      long totalElements) {
}
