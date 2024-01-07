package app.chatsservice.service.impl;

import app.chatsservice.dto.response.ConversationResponse;
import app.chatsservice.entity.Conversation;
import app.chatsservice.entity.ConversationMember;
import app.chatsservice.repository.ConversationMemberRepository;
import app.chatsservice.repository.ConversationRepository;
import app.chatsservice.service.ConversationService;
import app.chatsservice.utils.SystemDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final SystemDateTime systemDateTime;
    private final ConversationRepository conversationRepository;
    private final ConversationMemberRepository conversationMemberRepository;
    private final Function<String, DateTimeFormatter> formatterFunction = DateTimeFormatter::ofPattern;

    @Override
    public ConversationResponse getConversationById(Long conversationId) {
        // User id of the authenticated user
        Long authUserId = 1L;

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        List<ConversationMember> conversationMembers = conversationMemberRepository
                .findByConversationId(conversationId);

        if (CollectionUtils.isEmpty(conversationMembers) || conversationMembers.stream()
                .noneMatch(conversationMember -> conversationMember.getMemberId().equals(authUserId))) {
            throw new RuntimeException("Conversation member not found");
        }

        return ConversationResponse.builder()
                .id(String.valueOf(conversation.getId()))
                .conversationName(conversation.getConversationName())
                .avatar("link avatar")
                .createdAt(convertDateTimeToString(conversation.getCreatedAt()))
                .memberCount(String.valueOf(conversationMembers.size()))
                .members(conversationMembers.stream()
                        .map(conversationMember -> ConversationResponse.Member.builder()
                                .id(String.valueOf(conversationMember.getMemberId()))
                                .name("member name")
                                .nickname(conversationMember.getNickname())
                                .avatar("link avatar")
                                .build())
                        .toList())
                .isGroupChat(conversation.getIsGroupChat())
                .build();
    }

    private String convertDateTimeToString(LocalDateTime localDateTime) {
        long daysBetween = ChronoUnit.DAYS.between(localDateTime.toLocalDate(),
                systemDateTime.now().toLocalDate());

        if (daysBetween < 0) {
            throw new RuntimeException("Invalid date");
        } else if (daysBetween == 0) {
            return localDateTime.format(formatterFunction.apply("HH:mm"));
        } else if (daysBetween <= 7) {
            return localDateTime.format(formatterFunction.apply("EE HH:mm"));
        } else {
            return localDateTime.format(formatterFunction.apply("HH:mm, dd/MM/yyyy"));
        }
    }
}