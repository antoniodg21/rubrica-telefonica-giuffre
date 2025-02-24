package com.rubrica.dao;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    private final Connection connection;

    // Constructor injection della connessione
    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM user WHERE username=? AND password=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true se utente trovato
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
