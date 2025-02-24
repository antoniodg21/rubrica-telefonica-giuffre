package com.rubrica.view;

import com.rubrica.controller.ContactService;
import com.rubrica.dao.ContactDAO;
import com.rubrica.dao.ContactDAOImpl;
import com.rubrica.model.Contact;
import com.rubrica.persistance.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RubricaFrame extends JFrame {

    private JTable contactTable;
    private DefaultTableModel tableModel;
    private ContactService contactService;

    public RubricaFrame() {
        contactService = new ContactService(); // Usa ContactService
        initializeUI();
        loadContacts();
    }

    private void initializeUI() {
        setTitle("Rubrica Telefonica");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Table setup
        String[] columns = {"ID", "Nome", "Cognome", "Numero di Telefono"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        contactTable = new JTable(tableModel);
        contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(contactTable);

        // Nasconde la colonna ID (indice 0)
        contactTable.getColumnModel().getColumn(0).setMinWidth(0);
        contactTable.getColumnModel().getColumn(0).setMaxWidth(0);
        contactTable.getColumnModel().getColumn(0).setWidth(0);

        // Buttons
        JButton newButton = new JButton("Nuovo");
        JButton editButton = new JButton("Modifica");
        JButton deleteButton = new JButton("Elimina");

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(newButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Layout setup
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        newButton.addActionListener(e -> openEditor(null));

        editButton.addActionListener(e -> {
            int selectedRow = contactTable.getSelectedRow();
            if (selectedRow >= 0) {
                int contactId = (int) tableModel.getValueAt(selectedRow, 0);
                Contact contact = contactService.getContactById(contactId);
                openEditor(contact);
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un contatto da modificare.", "Nessuna selezione.", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = contactTable.getSelectedRow();
            if (selectedRow >= 0) {
                int contactId = (int) tableModel.getValueAt(selectedRow, 0);
                String fullName = tableModel.getValueAt(selectedRow, 1) + " " + tableModel.getValueAt(selectedRow, 2);

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Sicuro di voler eliminare " + fullName + "?",
                        "Conferma elimina",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    contactService.deleteContact(contactId);
                    loadContacts();
                    JOptionPane.showMessageDialog(this, "Contatto eliminato correttamente.", "Eliminato", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un contatto da eliminare.", "Nessuna selezione.", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void openEditor(Contact contact) {
        ContactEditorFrame editor = new ContactEditorFrame(this, contact);
        editor.setVisible(true);
        loadContacts();
    }

    private void loadContacts() {
        tableModel.setRowCount(0); // reset della tabella
        List<Contact> contacts = contactService.getAllContacts();
        for (Contact contact : contacts) {
            Object[] rowData = {
                    contact.getId(),
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getPhoneNumber()
            };
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RubricaFrame frame = new RubricaFrame();
            frame.setVisible(true);
        });
    }
}
