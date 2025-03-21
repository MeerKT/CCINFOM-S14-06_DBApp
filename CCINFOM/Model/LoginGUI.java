package Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginGUI extends JFrame implements ActionListener {

    private JTextField userField;
    private JPasswordField passField;
    private JComboBox<String> userTypeCombo;
    private JButton loginButton;

    public LoginGUI() {
        setTitle("Bank System Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Creating the components
        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel userTypeLabel = new JLabel("User Type:");
        
        userField = new JTextField(15);
        passField = new JPasswordField(15);

        String[] userTypes = {"Customer", "Employee"};
        userTypeCombo = new JComboBox<>(userTypes);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        // Setting up the layout
        setLayout(new GridLayout(4, 2, 5, 5));
        
        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(userTypeLabel);
        add(userTypeCombo);
        add(new JLabel()); // Empty cell
        add(loginButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        String userType = (String) userTypeCombo.getSelectedItem();

        if (authenticateUser(username, password, userType)) {
            JOptionPane.showMessageDialog(this, userType + " Login Successful!");
            // Open the respective dashboard based on userType
            if (userType.equals("Customer")) {
                // Open Customer Options GUI
            } else if (userType.equals("Employee")) {
                // Open Employee Options GUI
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
        }
    }

    private boolean authenticateUser(String username, String password, String userType) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) return false;

            String table = userType.equals("Customer") ? "customers" : "employees";
            String query = "SELECT * FROM " + table + " WHERE username = ? AND password = ?";
            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // Returns true if a record is found

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginGUI().setVisible(true);
        });
    }
}

