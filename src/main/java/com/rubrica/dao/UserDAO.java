package com.rubrica.dao;

import com.rubrica.model.User;

public interface UserDAO {
    boolean authenticateUser(String username, String password);
}
