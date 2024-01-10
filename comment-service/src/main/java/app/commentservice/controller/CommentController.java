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

    @PostMapping("/parent/add")
    public ResponseEntity<ParentCommentResponse> addParentComment(@RequestBody ParentCommentRequest parentCommentRequest){

        return new ResponseEntity<>(commentService.addParentComment(parentCommentRequest), HttpStatus.OK);
    }

    @GetMapping("/parent")
    public ResponseEntity<List<ParentCommentResponse>> getParentComment(@RequestParam long id){

        return new ResponseEntity<>(commentService.getListParentCommentByPostId(id), HttpStatus.OK);
    }

    @PutMapping("/parent/update")
    public ResponseEntity<ParentCommentResponse> updateParentComment(@RequestParam long id, @RequestParam String content){
        return new ResponseEntity<>(commentService.updateParentComment(id, content), HttpStatus.OK);
    }

    @DeleteMapping("/parent/delete")
    public ResponseEntity<Void> deleteParentComment(@RequestParam long id){
        commentService.deleteParentComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/child/add")
    public ResponseEntity<ChildCommentResponse> addChildComment(@RequestBody ChildCommentRequest childCommentRequest){
        return new ResponseEntity<>(commentService.addChildComment(childCommentRequest), HttpStatus.OK);
    }

    @PutMapping("/child/update")
    public ResponseEntity<ChildCommentResponse> updateChildComment(@RequestParam long id, @RequestParam String content){
        return new ResponseEntity<>(commentService.updateChildComment(id, content), HttpStatus.OK);
    }

    @DeleteMapping("/child/delete")
    public ResponseEntity<Void> deleteChildComment(@RequestParam long id){
        commentService.deleteChildComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
