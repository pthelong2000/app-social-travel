package app.chatsservice.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conversation")
public class Conversation extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conversation_name")
    private String conversationName;

    @Column(name = "is_group_chat", columnDefinition = "boolean default false")
    private Boolean isGroupChat;

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
