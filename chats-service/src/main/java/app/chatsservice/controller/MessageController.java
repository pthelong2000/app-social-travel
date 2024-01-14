package app.chatsservice.controller;

import app.chatsservice.dto.request.MessageRequest;
import app.chatsservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/conversation/{conversationId}/message")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public void sendMessage(@RequestBody MessageRequest request,
                            @PathVariable Long conversationId) {
        messageService.sendMessage(request, conversationId);
    }
}
