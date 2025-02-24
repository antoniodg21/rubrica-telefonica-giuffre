package com.rubrica.dao;

import com.rubrica.model.Contact;
import java.util.List;

public interface ContactDAO {
    void addContact(Contact contact);
    void updateContact(Contact contact);
    void deleteContact(int contactId);
    Contact getContactById(int contactId);
    List<Contact> getAllContacts();
}
