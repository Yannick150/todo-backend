package nl.yannick.todobackend.todo.dtos;

import jakarta.validation.constraints.NotNull;

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
}
