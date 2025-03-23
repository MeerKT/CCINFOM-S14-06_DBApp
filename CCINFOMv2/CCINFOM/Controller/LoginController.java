package Controller;

import Model.DatabaseConnection;
import View.CustomerOptionsGUI;
import View.EmployeeOptionsGUI;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private final JFrame loginFrame;

    public LoginController(JFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public void authenticateUser(String username, String password, String userType) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                JOptionPane.showMessageDialog(loginFrame, "Database connection failed.");
                return;
            }

            String table = userType.equals("Customer") ? "customers" : "employees";
            String query = "SELECT * FROM " + table + " WHERE username = ? AND password = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(loginFrame, userType + " Login Successful!");
                loginFrame.dispose(); // Close the login window

                if (userType.equals("Customer")) {
                    new CustomerOptionsGUI().setVisible(true);
                } else if (userType.equals("Employee")) {
                    new EmployeeOptionsGUI().setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials. Please try again.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loginFrame, "An error occurred while connecting to the database.");
        }
    }
}

