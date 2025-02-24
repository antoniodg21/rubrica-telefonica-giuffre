package com.rubrica.controller;

import com.rubrica.dao.ContactDAO;
import com.rubrica.dao.ContactDAOImpl;
import com.rubrica.model.Contact;
import com.rubrica.persistance.DatabaseConnection;

import java.util.List;

public class ContactService {

    private final ContactDAO contactDAO;

    public ContactService() {
        this.contactDAO = new ContactDAOImpl(DatabaseConnection.getConnection());
    }

    public void addContact(Contact contact) {
        contactDAO.addContact(contact);
    }

    public void updateContact(Contact contact) {
        contactDAO.updateContact(contact);
    }

    public void deleteContact(int contactId) {
        contactDAO.deleteContact(contactId);
    }

    public Contact getContactById(int contactId) {
        return contactDAO.getContactById(contactId);
    }

    public List<Contact> getAllContacts() {
        return contactDAO.getAllContacts();
    }
}
