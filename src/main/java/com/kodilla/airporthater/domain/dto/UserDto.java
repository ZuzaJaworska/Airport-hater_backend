package com.kodilla.airporthater.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String password;
    @Builder.Default
    private boolean blocked = false;
    @Builder.Default
    private List<Long> commentIds = new ArrayList<>();
}
