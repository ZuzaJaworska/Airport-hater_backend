package com.kodilla.airporthater.mapper;

import com.kodilla.airporthater.domain.dto.CommentDto;
import com.kodilla.airporthater.domain.entity.Airport;
import com.kodilla.airporthater.domain.entity.Comment;
import com.kodilla.airporthater.domain.entity.User;
import com.kodilla.airporthater.mapper.CommentMapper;
import com.kodilla.airporthater.repository.AirportRepository;
import com.kodilla.airporthater.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CommentMapperTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private CommentMapper commentMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldMapToCommentDto() {
        // Given
        User user = User.builder().id(1L).build();
        Airport airport = Airport.builder().iataCode("TES").build();
        Comment comment = Comment.builder()
                .id(1L)
                .title("Test Title")
                .body("Test Body")
                .score(5)
                .createdAt(LocalDateTime.now())
                .user(user)
                .airport(airport)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(airportRepository.findByIataCode("TES")).thenReturn(Optional.of(airport));

        // When
        CommentDto commentDto = commentMapper.mapToCommentDto(comment);

        // Then
        assertEquals(comment.getId(), commentDto.getId());
        assertEquals(comment.getTitle(), commentDto.getTitle());
        assertEquals(comment.getBody(), commentDto.getBody());
        assertEquals(comment.getScore(), commentDto.getScore());
        assertEquals(comment.getCreatedAt(), commentDto.getCreatedAt());
        assertEquals(comment.getUser().getId(), commentDto.getUserId());
        assertEquals(comment.getAirport().getIataCode(), commentDto.getAirportId());
    }

    @Test
    void shouldMapToCommentDtoList() {
        // Given
        User user = User.builder().id(1L).build();
        Airport airport = Airport.builder().iataCode("TES").build();
        Comment comment1 = Comment.builder()
                .id(1L)
                .title("Test Title 1")
                .body("Test Body 1")
                .score(5)
                .createdAt(LocalDateTime.now())
                .user(user)
                .airport(airport)
                .build();
        Comment comment2 = Comment.builder()
                .id(2L)
                .title("Test Title 2")
                .body("Test Body 2")
                .score(3)
                .createdAt(LocalDateTime.now())
                .user(user)
                .airport(airport)
                .build();
        List<Comment> comments = Arrays.asList(comment1, comment2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(airportRepository.findByIataCode("TES")).thenReturn(Optional.of(airport));

        // When
        List<CommentDto> commentDtos = commentMapper.mapToCommentDtoList(comments);

        // Then
        assertEquals(comments.size(), commentDtos.size());
        for (int i = 0; i < comments.size(); i++) {
            CommentDto commentDto = commentDtos.get(i);
            Comment comment = comments.get(i);
            assertEquals(comment.getId(), commentDto.getId());
            assertEquals(comment.getTitle(), commentDto.getTitle());
            assertEquals(comment.getBody(), commentDto.getBody());
            assertEquals(comment.getScore(), commentDto.getScore());
            assertEquals(comment.getCreatedAt(), commentDto.getCreatedAt());
            assertEquals(comment.getUser().getId(), commentDto.getUserId());
            assertEquals(comment.getAirport().getIataCode(), commentDto.getAirportId());
        }
    }

    @Test
    void shouldMapToComment() {
        // Given
        CommentDto commentDto = CommentDto.builder()
                .id(1L)
                .title("Test Title")
                .body("Test Body")
                .score(5)
                .createdAt(LocalDateTime.now())
                .userId(1L)
                .airportId("TES")
                .build();

        User user = User.builder().id(1L).build();
        Airport airport = Airport.builder().iataCode("TES").build();

        // Mock repository behavior
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(airportRepository.findByIataCode("TES")).thenReturn(Optional.of(airport));

        // When
        Comment comment = commentMapper.mapToComment(commentDto);

        // Then
        assertEquals(commentDto.getId(), comment.getId());
        assertEquals(commentDto.getTitle(), comment.getTitle());
        assertEquals(commentDto.getBody(), comment.getBody());
        assertEquals(commentDto.getScore(), comment.getScore());
        assertEquals(commentDto.getCreatedAt(), comment.getCreatedAt());
        assertEquals(commentDto.getUserId(), comment.getUser().getId());
        assertEquals(commentDto.getAirportId(), comment.getAirport().getIataCode());
    }
}
