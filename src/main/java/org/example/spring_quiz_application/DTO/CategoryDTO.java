package org.example.spring_quiz_application.DTO;

import lombok.*;
import org.example.spring_quiz_application.model.Category;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private int id;
    private String name;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
