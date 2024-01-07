package app.commentservice.entity;

import javax.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parent_comments")
public class ParentComment extends AbstractEntity implements Serializable {
    @Id
    @Column(name = "parent_comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "post_id")
    private long postId;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "image_id")
    private long imageId;

    @Column(name = "content")
    private String content;

}
