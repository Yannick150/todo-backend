package nl.yannick.todobackend.Todo.Dtos;

import jakarta.validation.constraints.NotNull;
import nl.yannick.todobackend.Todo.Todo;

import java.time.OffsetDateTime;
import java.util.*;

public record TodoResult(
        @NotNull UUID id,
        String name,
        String description,
        OffsetDateTime createdAt,
        OffsetDateTime deadline,
        List<String> tags,
        Boolean completed
) {
    public static TodoResult FromTodo(Todo todo){
        //TODO: improve
        var tags = new ArrayList<String>();
        if(todo.getTags() != null) {
            var tagsSplit = todo.getTags().split(",");
            tags = new ArrayList<String>(Arrays.asList(tagsSplit));
        }

        return new TodoResult(todo.getId(), todo.getName(), todo.getDescription(), todo.getCreatedAt(), todo.getDeadline(), tags, todo.isCompleted());
    }
}
