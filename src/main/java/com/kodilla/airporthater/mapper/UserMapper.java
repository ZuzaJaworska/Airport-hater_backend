package com.kodilla.airporthater.mapper;

import com.kodilla.airporthater.domain.entity.Comment;
import com.kodilla.airporthater.domain.entity.User;
import com.kodilla.airporthater.domain.dto.UserDto;
import com.kodilla.airporthater.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Builder
public class UserMapper {

    private final CommentRepository commentRepository;

    public UserDto mapToUserDto(User user) {
        List<Long> commentsIds = user.getComments().stream()
                .map(Comment::getId)
                .toList();
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .blocked(user.isBlocked())
                .commentIds(commentsIds)
                .build();
    }

    public List<UserDto> mapToUserDtoList(List<User> users) {
        return users.stream()
                .map(this::mapToUserDto)
                .toList();
    }

    public User mapToUser(UserDto userDto) {
        List<Comment> comments = userDto.getCommentIds().stream()
                .map(commentRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .blocked(userDto.isBlocked())
                .comments(comments)
                .build();
    }
}
