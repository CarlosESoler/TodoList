package com.carlos.todolistrocketseat.Domain.Controller;

import com.carlos.todolistrocketseat.Data.Model.Task;
import com.carlos.todolistrocketseat.Domain.Service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity createTask(@RequestBody Task taskDTO, HttpServletRequest request) {
        return taskService.createTask(taskDTO, request);
    }

    @GetMapping
    public ResponseEntity getTasks(HttpServletRequest request) {
        return taskService.getTasks(request);
    }

    @GetMapping("/{idTask}")
    public ResponseEntity getTaskById(@PathVariable UUID idTask) {
        return taskService.getTaskById(idTask);
    }

    @PutMapping("/{idTask}")
    public ResponseEntity updateTask(@RequestBody Task taskDTO, @PathVariable UUID idTask, HttpServletRequest request) {
        return taskService.updateTask(taskDTO, idTask, request);
    }
}
