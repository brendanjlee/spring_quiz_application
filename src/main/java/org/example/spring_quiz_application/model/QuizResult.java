package org.example.spring_quiz_application.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "quiz_result")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, name = "time_start")
    private LocalDateTime timeStart;

    @Column(nullable = false, name = "time_end")
    private LocalDateTime timeEnd;

    // relationships
    @OneToMany(mappedBy = "quiz_result", cascade = CascadeType.ALL)
    private List<QuizQuestion> quizQuestions;
}
