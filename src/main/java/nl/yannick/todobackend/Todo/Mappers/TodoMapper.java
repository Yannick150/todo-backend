package nl.yannick.todobackend.Todo.Mappers;

import nl.yannick.todobackend.Todo.Dtos.TodoResult;
import nl.yannick.todobackend.Todo.Dtos.TodoResultSet;
import nl.yannick.todobackend.Todo.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    @Mapping(target = "tags", source = "tags", qualifiedByName = "parseTags")
    TodoResult toResult(Todo todo);

    Set<TodoResult> toResultSet(List<Todo> todos);

    default TodoResultSet toWrappedResultSet(List<Todo> todos) {
        return new TodoResultSet(new HashSet<>(toResultSet(todos)));
    }

    // helper
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
