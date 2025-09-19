package nl.yannick.todobackend.Todo.Dtos;

import jakarta.validation.constraints.NotNull;
import nl.yannick.todobackend.Todo.Todo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record TodoResultSet(
        @NotNull Set<TodoResult> todos
){
    public static TodoResultSet fromTodos(List<Todo> todos){
        var result = new TodoResultSet(new HashSet<TodoResult>());
        for (Todo todo : todos) {
            result.todos.add(TodoResult.FromTodo(todo));
        }

        return result;
    }
}
