package app.chatsservice.service.impl;

import app.chatsservice.dto.response.ConversationNameResponse;
import app.chatsservice.dto.response.ConversationResponse;
import app.chatsservice.entity.Conversation;
import app.chatsservice.entity.ConversationMember;
import app.chatsservice.repository.ConversationCustomRepository;
import app.chatsservice.repository.ConversationMemberCustomRepository;
import app.chatsservice.repository.ConversationMemberRepository;
import app.chatsservice.repository.ConversationRepository;
import app.chatsservice.service.ConversationService;
import app.chatsservice.utils.SystemDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private SystemDateTime systemDateTime = new SystemDateTime();
    private final ConversationRepository conversationRepository;
    private final ConversationMemberRepository conversationMemberRepository;
    private final ConversationCustomRepository conversationCustomRepository;
    private final ConversationMemberCustomRepository conversationMemberCustomRepository;
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
                .conversationName(conversation.getConversationName()) // todo isGroupChat ? conversationName is null ? concat name of other member : conversationName  : name of the other member
                .avatar("link avatar")
                .createdAt(convertDateTimeToString(conversation.getCreateAt()))
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

    @Override
    public ConversationNameResponse updateConversationName(Long conversationId, String conversationName) {
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

        conversationCustomRepository.updateConversationName(conversationId, conversationName);

        return ConversationNameResponse.builder()
                .conversationId(String.valueOf(conversation.getId()))
                .conversationName(conversation.getConversationName())
                .build();
    }

    @Override
    public ConversationResponse createConversation(List<String> membersId) {
        //todo validate members id

        // User id of the authenticated user
        Long authUserId = 1L;

        if (CollectionUtils.isEmpty(membersId)) {
            throw new RuntimeException("Members id is empty");
        }

        if (membersId.size() == 1) {
            Long conversationId = conversationMemberCustomRepository
                    .findConversationIdByTwoMemberId(authUserId, Long.parseLong(membersId.get(0)));

            if (conversationId == null) {
                conversationId = conversationRepository.save(Conversation
                        .builder()
                        .createAt(systemDateTime.now())
                        .isGroupChat(false)
                        .build()).getId();

                conversationMemberRepository.saveAll(List.of(
                        ConversationMember.builder()
                                .conversationId(conversationId)
                                .memberId(authUserId)
                                .createAt(systemDateTime.now())
                                .build(),
                        ConversationMember.builder()
                                .conversationId(conversationId)
                                .memberId(Long.parseLong(membersId.get(0)))
                                .createAt(systemDateTime.now())
                                .build()));
            }

            return getConversationById(conversationId);
        }

        Long conversationId = conversationRepository.save(
                Conversation.builder()
                        .conversationName("name 1 concat name 2") //todo
                        .isGroupChat(true)
                        .createAt(systemDateTime.now())
                        .build()).getId();

        conversationMemberRepository.save(ConversationMember.builder()
                .conversationId(conversationId)
                .memberId(authUserId)
                .createAt(systemDateTime.now())
                .build());

        conversationMemberRepository.saveAll(membersId.stream().map(memberId ->
                ConversationMember.builder()
                    .conversationId(conversationId)
                    .memberId(Long.parseLong(memberId))
                    .createAt(systemDateTime.now())
                    .build())
                .toList());

        return getConversationById(conversationId);
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
