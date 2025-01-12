package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.DTO.*;
import org.example.spring_quiz_application.model.*;
import org.example.spring_quiz_application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizService {
    private final CategoryRepository categoryRepository;
    private final QuizResultRepository quizResultRepository;
    private final QuestionRepository questionRepository;
    private final QuizQuestionRepository quizQuestionRepository;
    private final UserRepository userRepository;
    private final ChoiceRepository choiceRepository;

    @Autowired
    public QuizService(CategoryRepository categoryRepository,
                       QuizResultRepository quizResultRepository,
                       QuestionRepository questionRepository,
                       QuizQuestionRepository quizQuestionRepository,
                       UserRepository userRepository,
                       ChoiceRepository choiceRepository) {

        this.categoryRepository = categoryRepository;
        this.quizResultRepository = quizResultRepository;
        this.questionRepository = questionRepository;
        this.quizQuestionRepository = quizQuestionRepository;
        this.userRepository = userRepository;
        this.choiceRepository = choiceRepository;

    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAllCategories();
    }

    public List<CategoryDTO> findAllCategoriesDTO() {
        return categoryRepository.findAllCategories().stream()
                .map(CategoryDTO::new).collect(Collectors.toList());
    }

    public Category findCategoryById(int categoryId) {
        return categoryRepository.findCategoryById(categoryId);
    }

    public CategoryDTO findCategoryDtoById(int categoryId) {
        return new CategoryDTO(categoryRepository.findCategoryById(categoryId));
    }

    public List<QuizResult> findQuizResultsByUserId(int userId) {
        return quizResultRepository.findQuizResultsByUserId(userId);
    }

    public QuizResult findQuizResultById(int quizResultId) {
        // map to dto at controller
        return quizResultRepository.findQuizResultById(quizResultId);
    }

    public QuizResultDTO findQuizResultDtoById(int quizResultId) {
        // 1. pull quiz results
        QuizResult quizResult = quizResultRepository.findQuizResultById(quizResultId);

        // 2. pull joined results
        List<QuizQuestion> quizQuestionList = quizResult.getQuizQuestions();
        List<Question> questionList = quizQuestionList.stream()
                .map(QuizQuestion::getQuestion).collect(Collectors.toList());
        List<Choice> userChoiceList = quizQuestionList.stream()
                .map(QuizQuestion::getUserChoice).collect(Collectors.toList());

        // 3. create DTO out of the joined tables
        List<ChoiceDTO> userChoiceDTOs = userChoiceList.stream()
                .map(ChoiceDTO::new).collect(Collectors.toList());
        List<QuestionDTO> questionDTOs = questionList.stream()
                .map(QuestionDTO::new).collect(Collectors.toList());
        questionDTOs.forEach(questionDTO -> {
            questionDTO.getChoices().forEach(choice -> {
                int choiceId = choice.getId();
                choice.setUserAnswer(userChoiceDTOs.stream().map(ChoiceDTO::getId).collect(Collectors.toList()).contains(choiceId));
            });
        });

        // 4. attach questions to DTO
        QuizResultDTO quizResultDTO = new QuizResultDTO(quizResult);
        quizResultDTO.setQuestions(questionDTOs);

        // 5. calculate result
        int result = 0;
        for (QuestionDTO questionDTO : quizResultDTO.getQuestions()) {
            for (ChoiceDTO choiceDTO : questionDTO.getChoices()) {
                if (choiceDTO.isAnswer() && choiceDTO.isUserAnswer()) {
                    result++;
                }
            }
        }
        quizResultDTO.setResult(result);

        return quizResultDTO;
    }

    public List<Question> findQuestionsByCategoryId(int categoryId) {
        return questionRepository.findQuestionsByCategoryId(categoryId);
    }

    public List<QuestionDTO> findQuestionDTOsByCategoryId(int categoryId) {
        return questionRepository.findQuestionsByCategoryId(categoryId).stream()
                .map(QuestionDTO::new).collect(Collectors.toList());
    }

    public List<Choice> findChoicesByQuestionId(int questionId) {
        return quizResultRepository.findChoicesByQuestionId(questionId);
    }

    public List<QuizQuestion> findQuizQuestionsByQuizResultId(int quizResultId) {
        List<QuizQuestion> quizQuestions = quizQuestionRepository.findAll();
        return quizQuestions.stream()
                .filter(quizQuestion -> quizQuestion.getQuizResult().getId() == quizResultId)
                .collect(Collectors.toList());
    }

    @Transactional
    public int submitQuizResult(int userId, QuizResultSubmitDTO quizResultSubmitDTO) {
        LocalDateTime endTime = LocalDateTime.now();

        System.out.println(quizResultSubmitDTO);

        // 1. create new quiz result
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User not found with ID: " + userId));
        Category category = categoryRepository.findById(quizResultSubmitDTO.getCategoryId()).orElseThrow(() ->
                new EntityNotFoundException("Category not found with ID: " + quizResultSubmitDTO.getCategoryId()));

        QuizResult quizResult = QuizResult.builder()
                .user(user)
                .category(category)
                .timeStart(quizResultSubmitDTO.getStartTime())
                .timeEnd(endTime)
                .build();

        quizResultRepository.save(quizResult); // todo extract Id

        // 2. create new quiz questions
        saveQuizQuestions(quizResult, quizResultSubmitDTO.getQuestionToChoiceMap());

        return quizResult.getId();
    }

    @Transactional
    public void saveQuizQuestions(QuizResult quizResult, Map<Integer, Integer> quizAnswers) {
        for (Map.Entry<Integer, Integer> entry : quizAnswers.entrySet()) {
            int questionId = entry.getKey();
            int choiceId = entry.getValue();

            Question question = questionRepository.findById(questionId).orElseThrow(() -> new EntityNotFoundException("Question not found with ID: " + questionId));
            Choice choice = choiceRepository.findById(choiceId).orElseThrow(() -> new EntityNotFoundException("Choice not found with ID: " + choiceId));

            QuizQuestion quizQuestion = QuizQuestion.builder()
                    .quizResult(quizResult)
                    .question(question)
                    .userChoice(choice)
                    .build();
            quizQuestionRepository.save(quizQuestion);
        }
    }
}

