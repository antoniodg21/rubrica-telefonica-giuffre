package com.rubrica.dao;

import com.rubrica.model.Contact;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl implements ContactDAO {

    private final Connection connection;

    // Constructor injection della connessione
    public ContactDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addContact(Contact contact) {
        String sql = "INSERT INTO contact (first_name, last_name, address, phone_number, age) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contact.getFirstName());
            stmt.setString(2, contact.getLastName());
            stmt.setString(3, contact.getAddress());
            stmt.setString(4, contact.getPhoneNumber());
            if (contact.getAge() != null) {
                stmt.setInt(5, contact.getAge());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateContact(Contact contact) {
        String sql = "UPDATE contact SET first_name=?, last_name=?, address=?, phone_number=?, age=? WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contact.getFirstName());
            stmt.setString(2, contact.getLastName());
            stmt.setString(3, contact.getAddress());
            stmt.setString(4, contact.getPhoneNumber());
            if (contact.getAge() != null) {
                stmt.setInt(5, contact.getAge());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            stmt.setInt(6, contact.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteContact(int contactId) {
        String sql = "DELETE FROM contact WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contactId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Contact getContactById(int contactId) {
        String sql = "SELECT * FROM contact WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, contactId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Contact(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        (Integer) rs.getObject("age")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contact";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getInt("age")
                );
                contacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }
}
