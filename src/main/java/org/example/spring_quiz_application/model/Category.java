package org.example.spring_quiz_application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "is_active")
    private boolean isActive = true;

    // relationships
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference("category-question")
    @ToString.Exclude
    private List<Question> questions;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonManagedReference("quizResults-category")
    @ToString.Exclude
    private List<QuizResult> quizResults;
}
