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
@Table(name = "conversation_member")
public class ConversationMember extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conversation_id", nullable = false)
    private Long conversationId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "nickname")
    private String nickname;

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
