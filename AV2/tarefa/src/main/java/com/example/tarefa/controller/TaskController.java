package com.example.tarefa.controller;

import com.example.tarefa.model.Role;
import com.example.tarefa.model.Task;
import com.example.tarefa.repository.TaskRepository;
import com.example.tarefa.repository.UserRepository;
import com.example.tarefa.service.CreateTask;
import com.example.tarefa.service.ListTask;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

   private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    // @GetMapping("/list")
    // public ResponseEntity<ListTask> feed(@RequestParam(value = "page", defaultValue = "0") int page,
    //                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

    //     var task = taskRepository.findAll(
    //             PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp"))
    //             .map(tweet ->
    //                     new ListItem(
    //                         task.getTweetId(),
    //                         task.getContent(),
    //                         task.getUser().getUsername())
    //             );

    //     return ResponseEntity.ok(new ListTask(
    //         task.getDescricao(), page, pageSize, task.getTotalPages(), task.getTotalElements()));
    // }


    @PostMapping
    public ResponseEntity<Void> createTweet(@RequestBody CreateTask dto,
                                            JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));

        var task = new Task();
        task.setUser(user.get());
        
        task.setDescricao(dto.descricao());
        // task.setConcluida(dto.concluida());

        taskRepository.save(task);

        return ResponseEntity.ok().build();
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long taskId,
                                            JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get().getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if (isAdmin || task.getUser().getId().equals(UUID.fromString(token.getName()))) {
            taskRepository.deleteById(taskId);

        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        return ResponseEntity.ok().build();
    }

}