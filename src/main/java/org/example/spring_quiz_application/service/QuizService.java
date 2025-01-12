package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.DTO.*;
import org.example.spring_quiz_application.model.*;
import org.example.spring_quiz_application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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

    public List<QuizResultDTO> findAllQuizResultDTO() {
        // 1. get all quiz results
        List<QuizResult> quizResults = quizResultRepository.findAll();

        return quizResults.stream()
                .sorted((qr1, qr2) -> qr2.getTimeStart().compareTo(qr1.getTimeStart()))
                .map(this::mapToQuizResultDTO)
                .collect(Collectors.toList());
    }

    public List<QuizResult> findQuizResultsByUserId(int userId) {
        return quizResultRepository.findQuizResultsByUserId(userId);
    }

    public QuizResult findQuizResultById(int quizResultId) {
        // map to dto at controller
        return quizResultRepository.findQuizResultById(quizResultId);
    }

    public QuizResultDTO findQuizResultDtoById(int quizResultId) {
        QuizResult quizResult = quizResultRepository.findQuizResultById(quizResultId);
        QuizResultDTO quizResultDTO = mapToQuizResultDTO(quizResult);
        return quizResultDTO;
    }

    public List<Question> findQuestionsByCategoryId(int categoryId) {
        return questionRepository.findQuestionsByCategoryId(categoryId);
    }

    public QuestionDTO findQuestionDtoById(int questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new EntityNotFoundException("Question not found with ID: " + questionId));
        return new QuestionDTO(question);
    }

    public List<QuestionDTO> findQuestionDTOsByCategoryId(int categoryId) {
        return questionRepository.findQuestionsByCategoryId(categoryId).stream()
                .map(QuestionDTO::new).collect(Collectors.toList());
    }

    public List<QuestionDTO> findAllQuestionDTOs() {
        List<QuestionDTO> questionDTOS = questionRepository.findAll().stream()
                .map(QuestionDTO::new).collect(Collectors.toList());

        List<CategoryDTO> categoryDTOs = findAllCategoriesDTO();
        Map<Integer, String> categoryIdToNameMap = new HashMap<>();
        for (CategoryDTO categoryDTO : categoryDTOs) {
            categoryIdToNameMap.put(categoryDTO.getId(), categoryDTO.getName());
        }

        for (QuestionDTO questionDTO : questionDTOS) {
            questionDTO.setCategoryName(categoryIdToNameMap.get(questionDTO.getCategoryId()));
        }
        questionDTOS.sort((q1, q2) -> q1.getCategoryName().compareTo(q2.getCategoryName()));

        return questionDTOS;
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

    public void toggleQuestionActive(int questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new EntityNotFoundException("Question not found with ID: " + questionId));
        question.setActive(!question.isActive());
        questionRepository.save(question);
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

    @Transactional
    public void updateQuestion(QuestionSubmitDTO questionSubmitDTO) {
        // 1. query question
        Question question = questionRepository.findById(
                questionSubmitDTO.getQuestionId()).orElseThrow(() -> new IllegalArgumentException("Invalid question ID")
        );

        // 2. update question text
        question.setText(questionSubmitDTO.getText());

        // 3. update choices
        List<ChoiceDTO> choiceDTOs = questionSubmitDTO.extractChoices();
        List<Choice> existingChoices = question.getChoices();

        for (int i = 0; i < choiceDTOs.size(); i++) {
            ChoiceDTO choiceDTO = choiceDTOs.get(i);
            Choice existingChoice = existingChoices.get(i);

            // Update existing choice
            existingChoice.setText(choiceDTO.getText());
            existingChoice.setAnswer(choiceDTO.isAnswer());
        }

        // Save the updated question (choices will be saved as well due to cascade)
        questionRepository.save(question);
        
    }

    @Transactional
    public void saveNewQuestion(QuestionSubmitDTO questionSubmitDTO) {
        // 1. get category name
        Category category = categoryRepository.findById(questionSubmitDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        // 2. create question
        Question question = Question.builder()
                .category(category)
                .text(questionSubmitDTO.getText())
                .isActive(true)
                .build();

        // 3. extract choices
        List<ChoiceDTO> choiceDTOS = questionSubmitDTO.extractChoices();
        List<Choice> choices = choiceDTOS.stream()
                .map(choiceDTO -> Choice.builder()
                        .question(question)
                        .text(choiceDTO.getText())
                        .isAnswer(choiceDTO.isAnswer())
                        .build())
                .collect(Collectors.toList());
        question.setChoices(choices);

        questionRepository.save(question);
    }

    /* Mapper */
    private QuizResultDTO mapToQuizResultDTO(QuizResult quizResult) {
        // 1. pull joined results
        List<QuizQuestion> quizQuestionList = quizResult.getQuizQuestions();
        List<Question> questionList = quizQuestionList.stream()
                .map(QuizQuestion::getQuestion).collect(Collectors.toList());
        List<Choice> userChoiceList = quizQuestionList.stream()
                .map(QuizQuestion::getUserChoice).collect(Collectors.toList());

        // 2. create DTO
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

        // 3. attach to DTO
        QuizResultDTO quizResultDTO = new QuizResultDTO(quizResult);
        quizResultDTO.setQuestions(questionDTOs);

        // 4. calculate result
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
}

