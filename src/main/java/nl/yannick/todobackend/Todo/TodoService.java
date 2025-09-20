package nl.yannick.todobackend.Todo;

import nl.yannick.todobackend.Todo.Dtos.CreateTodoRequest;
import nl.yannick.todobackend.Todo.Dtos.TodoResult;
import nl.yannick.todobackend.Todo.Dtos.TodoResultSet;
import nl.yannick.todobackend.Todo.Dtos.UpdateTodoRequest;
import nl.yannick.todobackend.Todo.Errors.TodoNotFoundException;
import nl.yannick.todobackend.Todo.Mappers.TodoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TodoService {
    private final TodoRepository repo;
    private final TodoMapper mapper;

    public TodoService(TodoRepository repo, TodoMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public TodoResultSet findAll() {
        var todo = repo.findAll();
        return mapper.toWrappedResultSet(todo); // of een PageDto
    }

    @Transactional(readOnly = true)
    public TodoResult findOne(UUID id) {
        var todo = repo.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        return mapper.toResult(todo);
    }

    @Transactional
    public TodoResult create(CreateTodoRequest req) {
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

    @Transactional
    public TodoResult patch(UUID id, UpdateTodoRequest req) {
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

    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id)) throw new TodoNotFoundException(id);
        repo.deleteById(id);
    }
}
