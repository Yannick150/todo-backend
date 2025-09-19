package nl.yannick.todobackend.Todo.Dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;
import java.util.Set;

public record CreateTodoRequest(
        @NotBlank String name,
        String description,
        Set<String> tags,
        OffsetDateTime deadline
) {
}
