package nl.yannick.todobackend.Todo.Dtos;

import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record TodoResultSet(
        @NotNull Set<TodoResult> todos
){
}
