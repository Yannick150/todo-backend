package nl.yannick.todobackend.todo.dtos;

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
