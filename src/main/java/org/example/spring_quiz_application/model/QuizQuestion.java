package org.example.spring_quiz_application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "quiz_question")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("quizResult-quizQuestion")
    @JoinColumn(name = "quiz_result_id", nullable = false)
    @ToString.Exclude
    private QuizResult quizResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonBackReference("question-quizQuestion")
    @ToString.Exclude
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_choice_id", nullable = false)
    @JsonBackReference("userChoice-quizQuestion")
    @ToString.Exclude
    private Choice userChoice;
}
