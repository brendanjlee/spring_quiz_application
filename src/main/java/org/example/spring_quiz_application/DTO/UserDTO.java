package org.example.spring_quiz_application.DTO;

import lombok.*;
import org.example.spring_quiz_application.model.User;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private boolean isAdmin;

    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.isActive = user.isActive();
        this.isAdmin = user.isAdmin();
    }
}
