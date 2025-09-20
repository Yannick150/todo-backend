package nl.yannick.todobackend.Todo;

import nl.yannick.todobackend.Todo.Dtos.CreateTodoRequest;
import nl.yannick.todobackend.Todo.Dtos.TodoResult;
import nl.yannick.todobackend.Todo.Dtos.TodoResultSet;
import nl.yannick.todobackend.Todo.Dtos.UpdateTodoRequest;
import nl.yannick.todobackend.Todo.Errors.TodoNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import nl.yannick.todobackend.Todo.Mappers.TodoMapper;

import java.util.List;
import java.util.UUID;

//TODO service?
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoRepository repo;
    private final TodoMapper mapper;

    public TodoController(TodoRepository repo, TodoMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @GetMapping
    public TodoResultSet findAll() {
        var todos = repo.findAll();
        return mapper.toWrappedResultSet(todos);
    }

    @GetMapping("/{id}")
    public TodoResult findOne(@PathVariable UUID id) {
        var todo = repo.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        return mapper.toResult(todo);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TodoResult create(@RequestBody @Valid CreateTodoRequest req) {
        var todo = new Todo();
        todo.setName(req.name());
        todo.setDescription(req.description());

        //TODO: duplicate
        if(req.tags() != null){
            var tagsString = String.join(",", req.tags());
            todo.setTags(tagsString);
        }

        todo.setDeadline(req.deadline());
        todo.setCompleted(false);
        var saved = repo.save(todo);
        return mapper.toResult(saved);
    }

    @PatchMapping("/{id}")
    public TodoResult update(@PathVariable UUID id, @RequestBody UpdateTodoRequest req) {
        var todo = repo.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        if (req.name() != null) todo.setName(req.name());
        if (req.description() != null) todo.setDescription(req.description());

        //TODO: duplicate
        if(req.tags() != null){
            var tagsString = String.join(",", req.tags());
            todo.setTags(tagsString);
        }

        if (req.deadline() != null) todo.setDeadline(req.deadline());
        if (req.completed() != null) todo.setCompleted(req.completed());
        var saved = repo.save(todo);
        return mapper.toResult(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        if (!repo.existsById(id)) throw new TodoNotFoundException(id);
        repo.deleteById(id);
    }

}
