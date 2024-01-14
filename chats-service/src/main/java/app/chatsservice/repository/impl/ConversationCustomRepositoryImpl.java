package app.chatsservice.repository.impl;

import app.chatsservice.repository.ConversationCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class ConversationCustomRepositoryImpl implements ConversationCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void updateConversationName(Long conversationId, String conversationName) {
        entityManager.createNativeQuery("UPDATE conversation SET conversation_name = ? WHERE id = ?")
                .setParameter(1, conversationName)
                .setParameter(2, conversationId)
                .executeUpdate();
    }
}
