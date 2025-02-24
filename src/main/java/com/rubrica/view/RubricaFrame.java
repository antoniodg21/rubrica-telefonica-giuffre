package com.rubrica.view;

import com.rubrica.controller.ContactService;
import com.rubrica.model.Contact;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
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

        String[] columns = {"ID", "Nome", "Cognome", "Numero di Telefono"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // Non modificabile
            }
        };

        contactTable = new JTable(tableModel);
        contactTable.getColumnModel().getColumn(0).setMinWidth(0);
        contactTable.getColumnModel().getColumn(0).setMaxWidth(0);
        contactTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(contactTable);

        scrollPane.getViewport().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                contactTable.clearSelection();
            }
        });

        Icon newIcon = scaleIcon(UIManager.getIcon("FileView.fileIcon"), 18, 18);
        Icon editIcon = scaleIcon(UIManager.getIcon("FileChooser.detailsViewIcon"), 18, 18);
        Icon deleteIcon = scaleIcon(UIManager.getIcon("OptionPane.errorIcon"), 18, 18);

        JToolBar toolBar = new JToolBar();
        JButton newButton = new JButton("Nuovo", newIcon);
        JButton editButton = new JButton("Modifica", editIcon);
        JButton deleteButton = new JButton("Elimina", deleteIcon);

        toolBar.add(newButton);
        toolBar.add(editButton);
        toolBar.add(deleteButton);

        // Layout
        setLayout(new BorderLayout());
        add(toolBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        newButton.addActionListener(e -> openEditor(null));

        editButton.addActionListener(e -> {
            int selectedRow = contactTable.getSelectedRow();
            if (selectedRow >= 0) {
                int contactId = (int) tableModel.getValueAt(selectedRow, 0);
                Contact contact = contactService.getContactById(contactId);
                openEditor(contact);
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un contatto da modificare.",
                        "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = contactTable.getSelectedRow();
            if (selectedRow >= 0) {
                int contactId = (int) tableModel.getValueAt(selectedRow, 0);
                String fullName = tableModel.getValueAt(selectedRow, 1) + " " + tableModel.getValueAt(selectedRow, 2);

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Vuoi davvero eliminare " + fullName + "?",
                        "Conferma Eliminazione",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    contactService.deleteContact(contactId);
                    loadContacts();
                    JOptionPane.showMessageDialog(this, "Contatto eliminato con successo.",
                            "Eliminato", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un contatto da eliminare.",
                        "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private Icon scaleIcon(Icon originalIcon, int width, int height) {
        BufferedImage bufferedImage = new BufferedImage(
                originalIcon.getIconWidth(), originalIcon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = bufferedImage.createGraphics();
        originalIcon.paintIcon(null, g, 0, 0);
        g.dispose();

        Image scaledImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
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
}
