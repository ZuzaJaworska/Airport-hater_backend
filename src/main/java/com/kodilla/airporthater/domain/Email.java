package com.kodilla.airporthater.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Email {

    private final String mailTo;
    private final String subject;
    private final String message;
}
