package app.chatsservice.controller;

import app.chatsservice.dto.request.ConversationMemberNicknameRequest;
import app.chatsservice.dto.response.ConversationMemberNicknameResponse;
import app.chatsservice.dto.response.ConversationMemberResponse;
import app.chatsservice.service.ConversationMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/conversation/{conversationId}/member")
public class ConversationMemberController {

    private final ConversationMemberService conversationMemberService;

    @PostMapping("{memberId}/update-nickname")
    public ResponseEntity<ConversationMemberNicknameResponse> updateNickname(
            @PathVariable("conversationId") Long conversationId,
            @PathVariable("memberId") Long memberId,
            @RequestBody ConversationMemberNicknameRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(conversationMemberService
                .updateConversationMemberNickname(conversationId, memberId, request.getNickname()));
    }

    @PostMapping("{memberId}/add")
    public ResponseEntity<ConversationMemberResponse> addMember(
            @PathVariable("conversationId") Long conversationId,
            @PathVariable("memberId") Long memberId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(conversationMemberService
                .addConversationMember(conversationId, memberId));
    }

    @PostMapping("{memberId}/remove")
    public ResponseEntity<ConversationMemberResponse> removeMember(
            @PathVariable("conversationId") Long conversationId,
            @PathVariable("memberId") Long memberId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(conversationMemberService
                .removeConversationMember(conversationId, memberId));
    }
}
