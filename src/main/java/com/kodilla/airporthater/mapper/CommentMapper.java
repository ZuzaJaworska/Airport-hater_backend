package com.kodilla.airporthater.mapper;

import com.kodilla.airporthater.domain.dto.CommentDto;
import com.kodilla.airporthater.domain.entity.Airport;
import com.kodilla.airporthater.domain.entity.Comment;
import com.kodilla.airporthater.domain.entity.User;
import com.kodilla.airporthater.repository.AirportRepository;
import com.kodilla.airporthater.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Builder
public class CommentMapper {

    private final UserRepository userRepository;
    private final AirportRepository airportRepository;

    public CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .title(comment.getTitle())
                .body(comment.getBody())
                .score(comment.getScore())
                .createdAt(comment.getCreatedAt())
                .userId(comment.getUser().getId())
                .airportId(comment.getAirport().getIataCode())
                .build();
    }

    public List<CommentDto> mapToCommentDtoList(List<Comment> comments) {
        return comments.stream()
                .map(this::mapToCommentDto)
                .toList();
    }

    public Comment mapToComment(CommentDto commentDto) {
        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(IllegalArgumentException::new);
        Airport airport = airportRepository.findByIataCode(commentDto.getAirportId())
                .orElseThrow(IllegalArgumentException::new);

        return Comment.builder()
                .id(commentDto.getId())
                .title(commentDto.getTitle())
                .body(commentDto.getBody())
                .score(commentDto.getScore())
                .createdAt(commentDto.getCreatedAt())
                .user(user)
                .airport(airport)
                .build();
    }
}
