package org.example.spring_quiz_application.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference("user-quizResults")
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference("quizResults-category")
    @ToString.Exclude
    private Category category;

    @Column(nullable = false, name = "time_start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH" +
            ":mm:ss")
    private LocalDateTime timeStart;

    @Column(nullable = false, name = "time_end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH" +
            ":mm:ss")
    private LocalDateTime timeEnd;

    // relationships
    @OneToMany(mappedBy = "quizResult", cascade = CascadeType.ALL)
    @JsonManagedReference("quizResult-quizQuestion")
    @ToString.Exclude
    private List<QuizQuestion> quizQuestions;
}
