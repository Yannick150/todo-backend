package nl.yannick.todobackend.todo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    @Setter
    @Column(name = "Id")
    private UUID id;

    @NotBlank
    @Getter
    @Setter
    private String name;

    //TODO should not be max
    @Column(columnDefinition = "nvarchar(max)")
    @Getter
    @Setter
    private String description;

    //TODO should not be max
    @Column(columnDefinition = "nvarchar(max)")
    @Getter
    @Setter
    private String tags;

    @Column(nullable = false)
    @Getter
    private OffsetDateTime createdAt;

    @Getter
    @Setter
    private OffsetDateTime deadline;

    @Column(nullable = false)
    @Getter
    @Setter
    private boolean completed;

    //Before first insert, check if there is a createdAt, otherwise insert it.
    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }
}
