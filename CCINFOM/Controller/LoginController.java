package controller;

import model.DatabaseConnection;
import view.CustomerOptionsGUI;
import view.EmployeeOptionsGUI;
import view.LoginGUI;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    private final LoginGUI loginView;

    public LoginController(LoginGUI loginView) {
        this.loginView = loginView;
    }

    public void authenticateUser(String username, String password, String userType) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                JOptionPane.showMessageDialog(loginView, "Database connection failed.");
                return;
            }

            String table = userType.equals("Customer") ? "customers" : "employees";
            String query = "SELECT * FROM " + table + " WHERE username = ? AND password = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(loginView, userType + " Login Successful!");
                loginView.dispose(); // Close the login window

                if (userType.equals("Customer")) {
                    new CustomerOptionsGUI().setVisible(true);
                } else if (userType.equals("Employee")) {
                    new EmployeeOptionsGUI().setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(loginView, "Invalid credentials. Please try again.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(loginView, "An error occurred while connecting to the database.");
        }
    }
}

