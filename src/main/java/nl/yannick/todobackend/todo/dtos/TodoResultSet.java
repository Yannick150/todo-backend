package nl.yannick.todobackend.todo.dtos;

import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record TodoResultSet(
        @NotNull Set<TodoResult> todos
){
}
