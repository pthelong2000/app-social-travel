package app.chatsservice.controller;

import app.chatsservice.dto.response.ConversationResponse;
import app.chatsservice.service.ConversationService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/create/{userId}")
    public ResponseEntity<ConversationResponse> createConversation() {
        return null;
    }
}
