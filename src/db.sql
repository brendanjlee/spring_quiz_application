DROP DATABASE IF EXISTS quiz_application;

CREATE DATABASE quiz_application;

USE quiz_application;

-- create tables --
-- contact
DROP TABLE IF EXISTS contact;

CREATE TABLE contact
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    subject        VARCHAR(255) NOT NULL,
    message        VARCHAR(255) NOT NULL,
    email          VARCHAR(255) NOT NULL,
    time_submitted TIMESTAMP    NOT NULL
);

-- user
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    email         VARCHAR(255) NOT NULL UNIQUE,
    first_name    VARCHAR(20)  NOT NULL,
    last_name     VARCHAR(20)  NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    is_admin      BOOLEAN DEFAULT FALSE,
    is_active     BOOLEAN DEFAULT TRUE
);

-- category
DROP TABLE IF EXISTS category;

CREATE TABLE category
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- question
DROP TABLE IF EXISTS question;

CREATE TABLE question
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    category_id int          NOT NULL,
    text        VARCHAR(255) NOT NULL,
    is_active   BOOLEAN DEFAULT TRUE,

    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE
);

-- choice
DROP TABLE IF EXISTS choice;

CREATE TABLE choice
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    question_id INT          NOT NULL,
    text        VARCHAR(255) NOT NULL,
    is_answer   BOOLEAN      NOT NULL,

    FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE
);

-- quiz
DROP TABLE IF EXISTS quiz_result;

CREATE TABLE quiz_result
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    user_id     INT         NOT NULL,
    category_id INT         NOT NULL,
    time_start  TIMESTAMP   NOT NULL,
    time_end    TIMESTAMP   NOT NULL,

    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE
);

-- quiz question
DROP TABLE IF EXISTS quiz_question;

CREATE TABLE quiz_question
(
    id             int PRIMARY KEY AUTO_INCREMENT,
    quiz_result_id        int NOT NULL,
    question_id    int NOT NULL,
    user_choice_id int NOT NULL,

    FOREIGN KEY (quiz_result_id) REFERENCES quiz_result (id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE,
    FOREIGN KEY (user_choice_id) REFERENCES choice (id) ON DELETE CASCADE
);

-- create example data --
-- admin
INSERT INTO user (first_name, last_name, email, password_hash, is_admin, is_active)
VALUES ('Brendan', 'Lee', 'brendanjlee5@gmail.com', 'password', TRUE, TRUE);

-- users
INSERT INTO user (first_name, last_name, email, password_hash, is_admin, is_active)
VALUES ('John', 'Doe', 'johndoe123@bmail.com', 'hashedpassword1', FALSE, TRUE),
       ('Jane', 'Smith', 'janesmith456@cmail.com', 'hashedpassword2', FALSE, TRUE),
       ('Emily', 'Johnson', 'emilyjohnson789@coldmail.com', 'hashedpassword3', FALSE, TRUE),
       ('Michael', 'Brown', 'michaelbrown321@bmail.com', 'hashedpassword4', FALSE, TRUE),
       ('Sarah', 'Davis', 'sarahdavis654@cmail.com', 'hashedpassword5', FALSE, TRUE),
       ('David', 'Miller', 'davidmiller987@coldmail.com', 'hashedpassword6', FALSE, TRUE),
       ('Laura', 'Wilson', 'laurawilson123@bmail.com', 'hashedpassword7', FALSE, TRUE),
       ('James', 'Taylor', 'jamestaylor456@cmail.com', 'hashedpassword8', FALSE, TRUE),
       ('Anna', 'Anderson', 'annaanderson789@coldmail.com', 'hashedpassword9', FALSE, TRUE),
       ('Chris', 'Moore', 'chrismoore321@bmail.com', 'hashedpassword10', FALSE, FALSE);

-- contact messages
INSERT INTO contact (subject, message, email, time_submitted)
VALUES ('Account Issue', 'I am unable to log into my account. Please help!', 'johndoe123@bmail.com',
        '2024-12-22 03:49:07'),
       ('Suggestion', 'Can you add more categories to the quizzes?', 'randomuser1@gmail.com', '2024-12-26 18:55:00'),
       ('Bug Report', 'The quiz timer resets if I refresh the page.', 'janesmith456@cmail.com', '2024-12-27 17:27:37'),
       ('Feedback', 'Great platform! I enjoy using it.', 'feedback_user@coldmail.com', '2024-12-26 12:42:34'),
       ('Complaint', 'The quizzes are too difficult. Please make them easier.', 'sarahdavis654@cmail.com',
        '2024-12-25 07:04:25'),
       ('Error Report', 'I got an error 500 while submitting my answers.', 'michaelbrown321@bmail.com',
        '2024-12-23 07:04:50'),
       ('General Inquiry', 'Do you plan to add leaderboard features?', 'generaluser@gmail.com', '2024-12-21 04:38:53'),
       ('Feature Request', 'Could you allow us to customize our profiles?', 'laurawilson123@bmail.com',
        '2024-12-30 10:18:55'),
       ('Payment Issue', 'I was charged twice for my subscription.', 'user_billing@coldmail.com',
        '2024-12-26 05:58:22'),
       ('Appreciation', 'Thanks for the amazing quizzes! Keep it up!', 'happyuser@cmail.com', '2024-12-30 16:48:55');

-- category
INSERT INTO category (name)
VALUES ('Animals'),
       ('Cars'),
       ('Food');

-- questions & choices
-- animal
INSERT INTO question (category_id, text, is_active)
VALUES (1, 'What is the fastest land animal?', TRUE),
       (1, 'Which animal is known as the king of the jungle?', TRUE),
       (1, 'What is the largest mammal in the world?', TRUE),
       (1, 'How many legs does a spider have?', TRUE),
       (1, 'Which bird is a universal symbol of peace?', TRUE),
       (1, 'What is a group of lions called?', TRUE),
       (1, 'Which animal is known to have a long memory?', TRUE),
       (1, 'What is the tallest animal in the world?', TRUE),
       (1, 'Which mammal can fly?', TRUE),
       (1, 'What is the most venomous snake in the world?', TRUE);

INSERT INTO choice (question_id, text, is_answer)
VALUES
-- Question 1
(1, 'Cheetah', TRUE),
(1, 'Lion', FALSE),
(1, 'Tiger', FALSE),
(1, 'Horse', FALSE),
-- Question 2
(2, 'Lion', TRUE),
(2, 'Elephant', FALSE),
(2, 'Tiger', FALSE),
(2, 'Giraffe', FALSE),
-- Question 3
(3, 'Blue Whale', TRUE),
(3, 'Elephant', FALSE),
(3, 'Shark', FALSE),
(3, 'Dolphin', FALSE),
-- Question 4
(4, 'Eight', TRUE),
(4, 'Six', FALSE),
(4, 'Ten', FALSE),
(4, 'Four', FALSE),
-- Question 5
(5, 'Dove', TRUE),
(5, 'Eagle', FALSE),
(5, 'Parrot', FALSE),
(5, 'Sparrow', FALSE),
-- Question 6
(6, 'Pride', TRUE),
(6, 'Herd', FALSE),
(6, 'Pack', FALSE),
(6, 'Flock', FALSE),
-- Question 7
(7, 'Elephant', TRUE),
(7, 'Dog', FALSE),
(7, 'Cat', FALSE),
(7, 'Horse', FALSE),
-- Question 8
(8, 'Giraffe', TRUE),
(8, 'Elephant', FALSE),
(8, 'Rhino', FALSE),
(8, 'Hippo', FALSE),
-- Question 9
(9, 'Bat', TRUE),
(9, 'Eagle', FALSE),
(9, 'Parrot', FALSE),
(9, 'Pigeon', FALSE),
-- Question 10
(10, 'Inland Taipan', TRUE),
(10, 'Cobra', FALSE),
(10, 'Rattlesnake', FALSE),
(10, 'Viper', FALSE);

-- cars
INSERT INTO question (category_id, text, is_active)
VALUES (2, 'Which car company makes the Mustang?', TRUE),
       (2, 'What does ABS stand for in cars?', TRUE),
       (2, 'Which country is the home of Ferrari?', TRUE),
       (2, 'What is the top-selling car of all time?', TRUE),
       (2, 'Which car manufacturer produces the Civic?', TRUE),
       (2, 'What is the most expensive car brand?', TRUE),
       (2, 'What does SUV stand for?', TRUE),
       (2, 'Which company owns Bugatti, Lamborghini, and Porsche?', TRUE),
       (2, 'What is the name of Tesla’s electric truck?', TRUE),
       (2, 'What year was the first car made?', TRUE);

INSERT INTO choice (question_id, text, is_answer)
VALUES
-- Question 1
(11, 'Ford', TRUE),
(11, 'Chevrolet', FALSE),
(11, 'Toyota', FALSE),
(11, 'BMW', FALSE),
-- Question 2
(12, 'Anti-lock Braking System', TRUE),
(12, 'Automatic Braking System', FALSE),
(12, 'Adaptive Brake System', FALSE),
(12, 'Advanced Brake System', FALSE),
-- Question 3
(13, 'Italy', TRUE),
(13, 'Germany', FALSE),
(13, 'USA', FALSE),
(13, 'Japan', FALSE),
-- Question 4
(14, 'Toyota Corolla', TRUE),
(14, 'Volkswagen Beetle', FALSE),
(14, 'Ford F-150', FALSE),
(14, 'Honda Accord', FALSE),
-- Question 5
(15, 'Honda', TRUE),
(15, 'Toyota', FALSE),
(15, 'Ford', FALSE),
(15, 'Hyundai', FALSE),
-- Question 6
(16, 'Rolls-Royce', TRUE),
(16, 'Ferrari', FALSE),
(16, 'Lamborghini', FALSE),
(16, 'Bugatti', FALSE),
-- Question 7
(17, 'Sport Utility Vehicle', TRUE),
(17, 'Special Utility Vehicle', FALSE),
(17, 'Small Utility Vehicle', FALSE),
(17, 'Safe Utility Vehicle', FALSE),
-- Question 8
(18, 'Volkswagen', TRUE),
(18, 'Toyota', FALSE),
(18, 'GM', FALSE),
(18, 'Ford', FALSE),
-- Question 9
(19, 'Cybertruck', TRUE),
(19, 'Model X', FALSE),
(19, 'Powertruck', FALSE),
(19, 'E-Truck', FALSE),
-- Question 10
(20, '1886', TRUE),
(20, '1900', FALSE),
(20, '1910', FALSE),
(20, '1920', FALSE);

-- food
INSERT INTO question (category_id, text, is_active)
VALUES (3, 'Which fruit is known as the king of fruits?', TRUE),
       (3, 'Which spice is the most expensive in the world?', TRUE),
       (3, 'What is the main ingredient in guacamole?', TRUE),
       (3, 'What is sushi traditionally wrapped in?', TRUE),
       (3, 'What type of pasta is shaped like a bow tie?', TRUE),
       (3, 'What is the national dish of Italy?', TRUE),
       (3, 'Which nut is used to make marzipan?', TRUE),
       (3, 'What type of bread is used in a traditional Reuben sandwich?', TRUE),
       (3, 'What fruit has its seeds on the outside?', TRUE),
       (3, 'What is the main ingredient in hummus?', TRUE);

INSERT INTO choice (question_id, text, is_answer)
VALUES
-- Question 1
(21, 'Mango', TRUE),
(21, 'Apple', FALSE),
(21, 'Banana', FALSE),
(21, 'Pineapple', FALSE),
-- Question 2
(22, 'Saffron', TRUE),
(22, 'Vanilla', FALSE),
(22, 'Cardamom', FALSE),
(22, 'Cinnamon', FALSE),
-- Question 3
(23, 'Avocado', TRUE),
(23, 'Tomato', FALSE),
(23, 'Lime', FALSE),
(23, 'Cucumber', FALSE),
-- Question 4
(24, 'Seaweed', TRUE),
(24, 'Rice Paper', FALSE),
(24, 'Nori', FALSE),
(24, 'Banana Leaf', FALSE),
-- Question 5
(25, 'Farfalle', TRUE),
(25, 'Spaghetti', FALSE),
(25, 'Penne', FALSE),
(25, 'Macaroni', FALSE),
-- Question 6
(26, 'Pizza', TRUE),
(26, 'Lasagna', FALSE),
(26, 'Risotto', FALSE),
(26, 'Carbonara', FALSE),
-- Question 7
(27, 'Almond', TRUE),
(27, 'Peanut', FALSE),
(27, 'Walnut', FALSE),
(27, 'Pistachio', FALSE),
-- Question 8
(28, 'Rye', TRUE),
(28, 'Wheat', FALSE),
(28, 'Sourdough', FALSE),
(28, 'White Bread', FALSE),
-- Question 9
(29, 'Strawberry', TRUE),
(29, 'Blueberry', FALSE),
(29, 'Grape', FALSE),
(29, 'Cherry', FALSE),
-- Question 10
(30, 'Chickpeas', TRUE),
(30, 'Lentils', FALSE),
(30, 'Beans', FALSE),
(30, 'Peas', FALSE);

-- Quiz Results
insert into quiz_result (user_id, category_id, time_start, time_end)
VALUES (2, 1, '2024-12-30 16:48:55', '2024-12-30 16:55:55');
insert into quiz_question (id, quiz_result_id, question_id, user_choice_id)
VALUES (1, 1, 1, 1),
       (2, 1, 2, 2),
       (3, 1, 3, 3),
       (4, 1, 4, 4),
       (5, 1, 5, 1),
       (6, 1, 6, 2),
       (7, 1, 7, 3),
       (8, 1, 8, 4),
       (9, 1, 9, 1),
       (10, 1, 10, 2);

insert into quiz_result (id, user_id, category_id, time_start, time_end)
VALUES (2, 2, 2, '2024-12-31 16:48:55', '2024-12-31 16:55:55');
insert into quiz_question (quiz_result_id, question_id, user_choice_id)
VALUES (2, 1, 4),
       (2, 2, 3),
       (2, 3, 2),
       (2, 4, 1),
       (2, 5, 1),
       (2, 6, 2),
       (2, 7, 3),
       (2, 8, 4),
       (2, 9, 5),
       (2, 10, 6);

insert into quiz_result (id, user_id, category_id, time_start, time_end)
VALUES (3, 2, 3, '2024-12-31 19:48:55', '2024-12-31 19:55:55');
insert into quiz_question (quiz_result_id, question_id, user_choice_id)
VALUES (3, 1, 4),
       (3, 2, 1),
       (3, 3, 2),
       (3, 4, 3),
       (3, 5, 4),
       (3, 6, 3),
       (3, 7, 2),
       (3, 8, 1),
       (3, 9, 1),
       (3, 10, 2);
