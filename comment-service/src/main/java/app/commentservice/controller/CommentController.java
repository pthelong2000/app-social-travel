package app.commentservice.controller;

import app.commentservice.dto.request.ChildCommentRequest;
import app.commentservice.dto.request.ParentCommentRequest;
import app.commentservice.dto.response.ChildCommentResponse;
import app.commentservice.dto.response.ParentCommentResponse;
import app.commentservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ParentCommentResponse> addParentComment(@RequestBody ParentCommentRequest parentCommentRequest){
        return new ResponseEntity<>(null);
    }

    @PostMapping
    public ResponseEntity<ChildCommentResponse> addChildComment(@RequestBody ChildCommentRequest childCommentRequest){
        return new ResponseEntity<>(null);
    }

    @GetMapping
    public ResponseEntity<List<ParentCommentResponse>> getParentComment(@RequestParam long id){

        return new ResponseEntity<>(commentService.getListParentCommentByPostId(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ChildCommentResponse> getChildComment(@RequestParam long id){
        return new ResponseEntity<>(null);
    }
}
