package com.kodilla.airporthater.controller;

import com.kodilla.airporthater.domain.dto.CommentDto;
import com.kodilla.airporthater.domain.entity.Comment;
import com.kodilla.airporthater.exception.exceptions.*;
import com.kodilla.airporthater.mapper.CommentMapper;
import com.kodilla.airporthater.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentMapper commentMapper;
    private final CommentService commentService;

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    // GET
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(commentMapper.mapToCommentDtoList(comments));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForOneUser(@PathVariable("userId") Long userId)
            throws UserNotFoundException {
        List<Comment> comments = commentService.getAllCommentsForOneUser(userId);
        return ResponseEntity.ok(commentMapper.mapToCommentDtoList(comments));
    }

    @GetMapping("/iata/{iataCode}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForOneAirport(@PathVariable("iataCode") String iataCode)
            throws AirportNotFoundException {
        List<Comment> comments = commentService.getAllCommentsForOneAirport(iataCode);
        return ResponseEntity.ok(commentMapper.mapToCommentDtoList(comments));
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("commentId") Long commentId)
            throws CommentNotFoundException {
        return ResponseEntity.ok(commentMapper.mapToCommentDto(commentService.getCommentById(commentId)));
    }

    // POST
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) throws FailedToCreateCommentException {
        Comment comment = commentMapper.mapToComment(commentDto);
        try {
            commentService.createComment(comment);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error creating comment: ", e);
            throw new FailedToCreateCommentException();
        }
    }

    // PUT
    @PutMapping
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto) throws CommentNotFoundException, FailedToCreateCommentException {
        Comment comment = commentMapper.mapToComment(commentDto);
        Comment updatedComment = commentService.createComment(comment);
        return ResponseEntity.ok(commentMapper.mapToCommentDto(updatedComment));
    }

    // DELETE
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId) throws CommentNotFoundException {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
