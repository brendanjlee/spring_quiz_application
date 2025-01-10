package org.example.spring_quiz_application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "choice")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference("question-choice")
    @ToString.Exclude
    private Question question;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false, name = "is_answer")
    private boolean isAnswer = false;

    // relationships
    @OneToMany(mappedBy = "userChoice", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonManagedReference("userChoice-quizQuestion")
    private List<QuizQuestion> quizQuestions;
}
