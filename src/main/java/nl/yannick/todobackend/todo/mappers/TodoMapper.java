package nl.yannick.todobackend.todo.mappers;

import nl.yannick.todobackend.todo.dtos.TodoResult;
import nl.yannick.todobackend.todo.dtos.TodoResultSet;
import nl.yannick.todobackend.todo.Todo;
import org.mapstruct.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    @Mapping(target = "tags", source = "tags", qualifiedByName = "parseTags")
    TodoResult toResult(Todo todo);

    Set<TodoResult> toResultSet(List<Todo> todos);

    default TodoResultSet toWrappedResultSet(List<Todo> todos) {
        //LinkedHashSet to keep ordering
        return new TodoResultSet(new LinkedHashSet<>(toResultSet(todos)));
    }

    @Named("parseTags")
    default List<String> parseTags(String tags) {
        if (tags == null || tags.isBlank()) return List.of();
        return Pattern.compile("\\s*,\\s*").splitAsStream(tags)
                .map(String::trim)
                .filter(t -> !t.isEmpty())
                .distinct()
                .toList();
    }
}
