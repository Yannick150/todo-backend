package nl.yannick.todobackend.todo.dtos;

import java.time.OffsetDateTime;
import java.util.Set;

public record UpdateTodoRequest(
        String name,
        String description,
        Set<String> tags,
        OffsetDateTime deadline,
        Boolean completed
) {
}
