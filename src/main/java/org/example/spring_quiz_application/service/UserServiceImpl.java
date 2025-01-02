package org.example.spring_quiz_application.service;

import org.example.spring_quiz_application.dao.UserDAO;
import org.example.spring_quiz_application.domain.User;
import org.example.spring_quiz_application.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        User user = userDAO.getUserById(id);
       

        return user;
    }

    @Override
    public void createUser(String email, String firstName, String lastName,
                           String passwordHash, boolean isAdmin,
                           boolean isActive) {
        userDAO.createUser(email, firstName, lastName, passwordHash, isAdmin,
                isActive);
    }

    public void createUser(String email, String firstName, String lastName,
                           String passwordHash) {
        this.createUser(email, firstName, lastName, passwordHash, false, true);
    }

    @Override
    public void updateUser(User user) {
        // todo check user exists
        // todo user validation
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        // todo check user exists
        userDAO.deleteUser(id);
    }

    @Override
    public void activateUser(int id) {
        // todo check privilege
        userDAO.activateUser(id);
    }

    @Override
    public void deactivateUser(int id) {
        // todo check privilege
        userDAO.deactivateUser(id);
    }

    @Override
    public void promoteUser(int id) {
        // todo check privilege
        userDAO.promoteUser(id);
    }

    @Override
    public void demoteUser(int id) {
        // todo check privilege
        userDAO.demoteUser(id);
    }
}
