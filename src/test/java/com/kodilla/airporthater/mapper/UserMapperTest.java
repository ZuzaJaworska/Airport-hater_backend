package com.kodilla.airporthater.mapper;

import com.kodilla.airporthater.domain.dto.UserDto;
import com.kodilla.airporthater.domain.entity.Comment;
import com.kodilla.airporthater.domain.entity.User;
import com.kodilla.airporthater.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserMapperTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldMapToUserDto() {
        // given
        User user = new User(1L, "testuser", "password", false);
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(1L, "Test Comment", "This is a test comment", 5, user));
        user.setComments(comments);

        // when
        UserDto userDto = userMapper.mapToUserDto(user);

        // then
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.isBlocked(), userDto.isBlocked());
        assertEquals(1, userDto.getCommentIds().size());
        assertEquals(1L, userDto.getCommentIds().get(0));
    }

    @Test
    void shouldMapToUser() {
        // given
        UserDto userDto = new UserDto(1L, "testuser", "password", false, List.of(1L));

        Comment comment = new Comment(1L, "Test Comment", "This is a test comment", 5, null);
        when(commentRepository.findById(1L)).thenReturn(java.util.Optional.of(comment));

        // when
        User user = userMapper.mapToUser(userDto);

        // then
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.isBlocked(), user.isBlocked());
        assertEquals(1, user.getComments().size());
        assertEquals(comment, user.getComments().get(0));
    }
}
