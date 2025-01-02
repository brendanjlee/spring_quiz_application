package org.example.spring_quiz_application.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private boolean isAdmin;
    private boolean isActive;
}
