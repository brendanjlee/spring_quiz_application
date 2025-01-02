package org.example.spring_quiz_application.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contact {
    private int id;
    private String subject;
    private String message;
    private String email;
    private LocalDateTime timeSubmitted;
}
