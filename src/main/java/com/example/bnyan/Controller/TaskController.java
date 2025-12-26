package com.example.bnyan.Controller;

import com.example.bnyan.Api.ApiResponse;
import com.example.bnyan.Model.Task;
import com.example.bnyan.Service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.status(200).body(taskService.get());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid Task task) {

        taskService.add(task);
        return ResponseEntity.status(200).body(new ApiResponse("Task added"));
    }

    @PutMapping("/update-status/{taskId}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable Integer taskId, @PathVariable String status) {

        taskService.updateStatus(taskId, status);
        return ResponseEntity.status(200).body(new ApiResponse("Task status updated"));
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<?> delete(@PathVariable Integer taskId) {

        taskService.delete(taskId);
        return ResponseEntity.status(200).body(new ApiResponse("Task deleted"));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(taskService.getTaskById(id));
    }

}
