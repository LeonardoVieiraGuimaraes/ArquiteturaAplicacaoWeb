package com.cadastro.tarefas.controller;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.cadastro.tarefas.dto.CreateTaskDto;
import com.cadastro.tarefas.dto.TaskDto;
import com.cadastro.tarefas.dto.TaskItemDto;
import com.cadastro.tarefas.model.Role;
import com.cadastro.tarefas.model.Task;
import com.cadastro.tarefas.repository.TaskRepository;
import com.cadastro.tarefas.repository.UserRepository;


import java.util.UUID;

@RestController
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/task")
    public ResponseEntity<TaskDto> feed(@RequestParam(value = "page", defaultValue = "0") int page,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        var tasks = taskRepository.findAll(
                PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
                .map(task ->
                        new TaskItemDto(
                            task.getId(),
                            task.getUser().getUsername(),
                            task.getDescricao(),
                            task.getConcluida())
                );
        return ResponseEntity.ok(new TaskDto(
            tasks.getContent(), page, pageSize, tasks.getTotalPages(), tasks.getTotalElements()));
    }

    @PostMapping("/task")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTaskDto dto,
                                            JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));

        var task = new Task();
        task.setUser(user.get());
        task.setDescricao(dto.descricao());

        taskRepository.save(task);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long taskId,
                                            JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));
        var tweet = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get().getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if (isAdmin || tweet.getUser().getId().equals(UUID.fromString(token.getName()))) {
            taskRepository.deleteById(taskId);

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        return ResponseEntity.ok().build();
    }
}
