package app.chatsservice.controller;

import app.chatsservice.dto.request.ConversationNameRequest;
import app.chatsservice.dto.request.NewConversationRequest;
import app.chatsservice.dto.response.ConversationNameResponse;
import app.chatsservice.dto.response.ConversationResponse;
import app.chatsservice.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationResponse> getConversationById(
            @PathVariable("conversationId") Long conversationId) {
        return ResponseEntity.ok(conversationService.getConversationById(conversationId));
    }

    @PostMapping("/create")
    public ResponseEntity<ConversationResponse> createConversation(
            @RequestBody NewConversationRequest newConversationRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(conversationService.createConversation(
                        newConversationRequest.getMembersId()));
    }

    @PatchMapping("/update/{conversationId}/name")
    public ResponseEntity<ConversationNameResponse> updateConversationName(
            @PathVariable("conversationId") Long conversationId,
            @RequestBody ConversationNameRequest conversationNameRequest) {
        return ResponseEntity.ok(conversationService.updateConversationName(
                conversationId, conversationNameRequest.getConversationName()));
    }

}
