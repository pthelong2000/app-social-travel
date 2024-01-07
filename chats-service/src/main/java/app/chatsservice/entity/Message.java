package app.chatsservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class Message extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content")
    private String content;

    @Column(name = "sender_id", nullable = false)
    private long senderId;

    @Column(name = "receiver_id", nullable = false)
    private long receiverId;

    @Column(name = "file_path")
    private String filePath;

    @Override
    public LocalDateTime getCreatedAt() {
        return super.createAt;
    }

    @Override
    public String getCreatedBy() {
        return super.createdBy;
    }

    @Override
    public LocalDateTime getLastModifiedAt() {
        return super.lastModifiedAt;
    }

    @Override
    public String getLastModifiedBy() {
        return super.lastModifiedBy;
    }
}
