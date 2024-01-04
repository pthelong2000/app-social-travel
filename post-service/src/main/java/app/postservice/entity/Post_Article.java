package app.postservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_article")
public class Post_Article extends AbstractEntity implements Serializable {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_content")
    private String content;

    @Column(name = "post_image")
    private Long idImage;

    @Column(name = "post_author")
    private String author;
}
