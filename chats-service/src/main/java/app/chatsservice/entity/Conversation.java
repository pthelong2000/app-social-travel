package app.chatsservice.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@Entity
@SuperBuilder
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
}
