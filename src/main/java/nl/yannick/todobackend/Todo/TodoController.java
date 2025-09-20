package nl.yannick.todobackend.Todo;

import nl.yannick.todobackend.Todo.Dtos.CreateTodoRequest;
import nl.yannick.todobackend.Todo.Dtos.TodoResult;
import nl.yannick.todobackend.Todo.Dtos.TodoResultSet;
import nl.yannick.todobackend.Todo.Dtos.UpdateTodoRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public TodoResultSet findAll() {
        return this.todoService.findAll();
    }

    @GetMapping("/{id}")
    public TodoResult findOne(@PathVariable UUID id) {
        return this.todoService.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TodoResult create(@RequestBody @Valid CreateTodoRequest req) {
        return this.todoService.create(req);
    }

    @PatchMapping("/{id}")
    public TodoResult update(@PathVariable UUID id, @RequestBody UpdateTodoRequest req) {
        return this.todoService.patch(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        this.todoService.delete(id);
    }

}
