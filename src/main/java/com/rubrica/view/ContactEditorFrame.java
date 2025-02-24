package com.rubrica.view;

import com.rubrica.controller.ContactService;
import com.rubrica.dao.ContactDAO;
import com.rubrica.dao.ContactDAOImpl;
import com.rubrica.model.Contact;
import com.rubrica.persistance.DatabaseConnection;

import javax.swing.*;
import java.awt.*;

public class ContactEditorFrame extends JDialog {

    private JTextField firstNameField, lastNameField, addressField, phoneField, ageField;
    private JButton saveButton, cancelButton;
    private ContactService contactService;
    private Contact contact;

    public ContactEditorFrame(JFrame parent, Contact contact) {
        super(parent, "Contatto Editor", true);
        this.contact = contact;
        contactService = new ContactService(); // Usa ContactService

        initializeUI();
        if (contact != null) {
            populateFields();
        }

        setSize(350, 300);
        setLocationRelativeTo(parent);
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nome:"));
        firstNameField = new JTextField();
        panel.add(firstNameField);

        panel.add(new JLabel("Cognome:"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        panel.add(new JLabel("Indirizzo:"));
        addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("Numero di Telefono:"));
        phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(new JLabel("Età:"));
        ageField = new JTextField();
        panel.add(ageField);

        saveButton = new JButton("Salva");
        cancelButton = new JButton("Cancella");

        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);

        saveButton.addActionListener(e -> saveContact());
        cancelButton.addActionListener(e -> dispose());
    }

    private void populateFields() {
        firstNameField.setText(contact.getFirstName());
        lastNameField.setText(contact.getLastName());
        addressField.setText(contact.getAddress());
        phoneField.setText(contact.getPhoneNumber());
        ageField.setText((String.valueOf(contact.getAge()).equals("null") ? "" : String.valueOf(contact.getAge())));
    }

    private void saveContact() {
        if (!validateFields()) {
            return;
        }

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        Integer age;

        try {
            if(ageField.getText().isEmpty())
                age = null;
            else
                age = Integer.parseInt(ageField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Età non valida", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (contact == null) {
            contact = new Contact(firstName, lastName, address, phone, age);
            contactService.addContact(contact);
        } else {
            contact.setFirstName(firstName);
            contact.setLastName(lastName);
            contact.setAddress(address);
            contact.setPhoneNumber(phone);
            contact.setAge(age);
            contactService.updateContact(contact);
        }

        JOptionPane.showMessageDialog(this, "Contatto salvato correttamente.", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    private boolean validateFields() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (firstName.isEmpty() && lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "È obbligatorio inserire almeno uno tra nome o cognome!",
                    "Errore salvataggio", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il numero di telefono è obbligatorio.",
                    "Errore salvataggio", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validazione formato numero di telefono
        if (!phone.matches("^\\+?\\d+$")) {
            JOptionPane.showMessageDialog(this,
                    "Numero di telefono non valido. Può contenere solo numeri ed eventialmente iniziare con '+'.",
                    "Errore salvataggio", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true; // tutti i controlli passati
    }
}
