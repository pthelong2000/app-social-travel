package app.commentservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "child_comments")
public class ChildComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long parentCommentId;
    private long userId;
    private String content;
    private boolean isFavorite;
}
