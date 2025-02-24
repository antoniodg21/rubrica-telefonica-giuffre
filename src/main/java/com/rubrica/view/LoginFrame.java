package com.rubrica.view;

import com.rubrica.controller.LoginService;
import com.rubrica.dao.UserDAO;
import com.rubrica.dao.UserDAOImpl;
import com.rubrica.persistance.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    // Nel costruttore di LoginFrame:
    private LoginService loginService;

    public LoginFrame() {
        loginService = new LoginService(); // Usa il service
        initializeUI();
    }


    private void initializeUI() {
        setTitle("Login");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create fields panel
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        fieldsPanel.add(usernameLabel);
        fieldsPanel.add(usernameField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Login");
        buttonPanel.add(loginButton);

        // Add listeners
        loginButton.addActionListener(new LoginActionListener());

        // Add panels to frame
        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Inserire Username e Password.",
                        "Errore Login",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try{
                boolean isAuthenticated = loginService.authenticate(username, password); // utilizza il service

                if (isAuthenticated) {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Login effettuato!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    SwingUtilities.invokeLater(() -> {
                        dispose();
                        RubricaFrame rubricaFrame = new RubricaFrame();
                        rubricaFrame.setVisible(true);
                    });

                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Username o password non corretti.",
                            "Login fallito",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(LoginFrame.this,
                        "Unable to connect to the database. Please check your connection settings.",
                        "Database Connection Error",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }

        }
    }
}
