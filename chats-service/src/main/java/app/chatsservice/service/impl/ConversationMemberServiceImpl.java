package app.chatsservice.service.impl;

import app.chatsservice.dto.response.ConversationAllMemberResponse;
import app.chatsservice.dto.response.ConversationMemberNicknameResponse;
import app.chatsservice.dto.response.ConversationMemberResponse;
import app.chatsservice.entity.ConversationMember;
import app.chatsservice.repository.ConversationCustomRepository;
import app.chatsservice.repository.ConversationMemberCustomRepository;
import app.chatsservice.repository.ConversationMemberRepository;
import app.chatsservice.repository.ConversationRepository;
import app.chatsservice.service.ConversationMemberService;
import app.chatsservice.utils.SystemDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ConversationMemberServiceImpl implements ConversationMemberService {

    private final SystemDateTime systemDateTime;
    private final ConversationRepository conversationRepository;
    private final ConversationMemberRepository conversationMemberRepository;
    private final ConversationMemberCustomRepository conversationMemberCustomRepository;

    @Override
    public ConversationMemberNicknameResponse updateConversationMemberNickname(Long conversationId, Long memberId, String nickname) {
        // User id of the authenticated user
        Long authUserId = 1L;

        conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        List<ConversationMember> conversationMembers = conversationMemberRepository
                .findByConversationId(conversationId);

        if (CollectionUtils.isEmpty(conversationMembers) || conversationMembers.stream()
                .noneMatch(conversationMember -> conversationMember.getMemberId().equals(authUserId)
                        || conversationMember.getMemberId().equals(memberId))) {
            throw new RuntimeException("Conversation member not found");
        }

        conversationMemberCustomRepository.updateConversationMemberNickname(conversationId, memberId, nickname);

        return ConversationMemberNicknameResponse.builder()
                .conversationId(String.valueOf(conversationId))
                .memberId(String.valueOf(memberId))
                .nickname(nickname)
                .build();
    }

    @Override
    public ConversationMemberResponse addConversationMember(Long conversationId, Long memberId) {
        // User id of the authenticated user
        Long authUserId = 1L;

        // todo check memberId exists in user table

        conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        if (!conversationMemberRepository.existsByConversationIdAndMemberId(conversationId, authUserId)) {
            throw new RuntimeException("Conversation member not found");
        }

        if (conversationMemberRepository.existsByConversationIdAndMemberId(conversationId, memberId)) {
            throw new RuntimeException("Conversation member already exists");
        }

        ConversationMember conversationMember = ConversationMember.builder()
                .conversationId(conversationId)
                .memberId(memberId)
                .createAt(systemDateTime.now())
                .build();

        conversationMemberRepository.save(conversationMember);

        return ConversationMemberResponse.builder()
                .conversationId(String.valueOf(conversationId))
                .memberId(String.valueOf(memberId))
                .build();
    }

    @Override
    public ConversationMemberResponse removeConversationMember(Long conversationId, Long memberId) {
        // User id of the authenticated user
        Long authUserId = 1L;

        conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        if (!conversationMemberRepository.existsByConversationIdAndMemberId(conversationId, authUserId)) {
            throw new RuntimeException("Conversation member not found");
        }

        if (!conversationMemberRepository.existsByConversationIdAndMemberId(conversationId, memberId)) {
            throw new RuntimeException("Conversation member not found");
        }

        conversationMemberRepository.deleteByConversationIdAndMemberId(conversationId, memberId);
        return ConversationMemberResponse.builder()
                .conversationId(String.valueOf(conversationId))
                .memberId(String.valueOf(memberId))
                .build();
    }

    @Override
    public ConversationAllMemberResponse getConversationMembers(Long conversationId) {
        // User id of the authenticated user
        Long authUserId = 1L;

        conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        if (!conversationMemberRepository.existsByConversationIdAndMemberId(conversationId, authUserId)) {
            throw new RuntimeException("Conversation member not found");
        }

        List<ConversationMember> conversationMembers = conversationMemberRepository
                .findByConversationId(conversationId);

        return ConversationAllMemberResponse.builder()
                .conversationId(String.valueOf(conversationId))
                .members(conversationMembers.stream().map(conversationMember -> ConversationAllMemberResponse.Member.builder()
                        .id(String.valueOf(conversationMember.getMemberId()))
                        .nickname(conversationMember.getNickname())
                        .name("name of member")
                        .build()).toList())
                .build();
    }
}
