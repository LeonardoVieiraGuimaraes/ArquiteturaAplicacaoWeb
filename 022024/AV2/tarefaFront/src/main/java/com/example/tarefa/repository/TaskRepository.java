package com.example.tarefa.repository;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tarefa.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // List<Task> findByUser(User user);
}