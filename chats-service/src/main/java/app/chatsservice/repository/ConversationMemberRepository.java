package app.chatsservice.repository;

import app.chatsservice.entity.ConversationMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ConversationMemberRepository extends JpaRepository<ConversationMember, Long> {

    List<ConversationMember> findByConversationId(Long conversationId);

    Boolean existsByConversationIdAndMemberId(Long conversationId, Long memberId);

    @Transactional
    void deleteByConversationIdAndMemberId(Long conversationId, Long memberId);


}
