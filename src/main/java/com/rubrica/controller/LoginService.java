package com.rubrica.controller;

import com.rubrica.dao.UserDAO;
import com.rubrica.dao.UserDAOImpl;
import com.rubrica.persistance.DatabaseConnection;

public class LoginService {

    private UserDAO userDAO;

    public LoginService() {
        userDAO = new UserDAOImpl(DatabaseConnection.getConnection());
    }

    public boolean authenticate(String username, String password) {
        return userDAO.authenticateUser(username, password);
    }
}
