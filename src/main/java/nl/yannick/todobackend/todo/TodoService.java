package nl.yannick.todobackend.todo;

import nl.yannick.todobackend.todo.dtos.CreateTodoRequest;
import nl.yannick.todobackend.todo.dtos.TodoResult;
import nl.yannick.todobackend.todo.dtos.TodoResultSet;
import nl.yannick.todobackend.todo.dtos.UpdateTodoRequest;
import nl.yannick.todobackend.todo.errors.TodoNotFoundException;
import nl.yannick.todobackend.todo.mappers.TodoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
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
        todo.setTags(setTagsIfPresent(Optional.ofNullable(req.tags())));

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
        todo.setTags(setTagsIfPresent(Optional.ofNullable(req.tags())));

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

    private String setTagsIfPresent(Optional<Set<String>> tags){
        return tags.map(strings -> String.join(",", strings)).orElse(null);
    }
}
