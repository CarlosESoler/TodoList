package com.carlos.todolistrocketseat.Controller;

import com.carlos.todolistrocketseat.Model.Task;
import com.carlos.todolistrocketseat.Repository.TaskRepository;
import com.carlos.todolistrocketseat.Service.TaskService;
import com.carlos.todolistrocketseat.Utils.UtilsProp;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity createTask(@RequestBody Task task, HttpServletRequest request) {
        return taskService.createTask(task, request);
    }

    @GetMapping
    public ResponseEntity getTasks(HttpServletRequest request) {
        return taskService.getTasks(request);
    }

    @GetMapping("/{idTask}")
    public ResponseEntity getTaskById(@PathVariable UUID idTask, HttpServletRequest request) {
        return taskService.getTaskById(idTask, request);
    }

    @PutMapping("/{idTask}")
    public ResponseEntity updateTask(@RequestBody Task task, @PathVariable UUID idTask, HttpServletRequest request) {
        return taskService.updateTask(task, idTask, request);
    }
}
