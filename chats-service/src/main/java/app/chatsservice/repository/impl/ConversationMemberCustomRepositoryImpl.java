package app.chatsservice.repository.impl;

import app.chatsservice.repository.ConversationMemberCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Repository
@Transactional
@RequiredArgsConstructor
public class ConversationMemberCustomRepositoryImpl implements ConversationMemberCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public Long findConversationIdByTwoMemberId(Long firstMemberId, Long secondMemberId) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT cm.conversationId ");
        queryBuilder.append("FROM ConversationMember cm ");
        queryBuilder.append("WHERE cm.conversationId IN ");
        queryBuilder.append("   (SELECT cm1.conversationId ");
        queryBuilder.append("    FROM ConversationMember cm1 ");
        queryBuilder.append("    WHERE cm1.memberId = :firstMemberId) ");
        queryBuilder.append("AND cm.conversationId IN ");
        queryBuilder.append("   (SELECT cm2.conversationId ");
        queryBuilder.append("    FROM ConversationMember cm2 ");
        queryBuilder.append("    WHERE cm2.memberId = :secondMemberId) ");
        queryBuilder.append("GROUP BY cm.conversationId ");
        queryBuilder.append("HAVING COUNT(cm.conversationId) = 2");

        Query query = entityManager.createQuery(queryBuilder.toString());
        query.setParameter("firstMemberId", firstMemberId);
        query.setParameter("secondMemberId", secondMemberId);

        return query.getResultList().isEmpty() ? null : (Long) query.getSingleResult();
    }
}
