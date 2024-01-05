package app.commentservice.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "child_comments")
public class ChildComment extends AbstractEntity implements Serializable {
    @Id
    @Column(name = "child_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "parent_comment_id")
    private long parentCommentId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "image_id")
    private long imageId;

    @Column(name = "content")
    private String content;
}
