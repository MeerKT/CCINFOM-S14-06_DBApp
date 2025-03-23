package Controller;

import Model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerGUI {
    public static void signUpGUI() {
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField dobField = new JTextField();
        JButton submitButton = new JButton("Sign Up");

        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email Address:"));
        panel.add(emailField);
        panel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        panel.add(dobField);
        panel.add(submitButton);

        submitButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String dob = dobField.getText();

            // Call the signUp method
            boolean success = Customer.signUp(firstName, lastName, phone, email, dob);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Sign Up Successful!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Sign Up Failed! User may already exist.");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void loginGUI() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JButton loginButton = new JButton("Login");

        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();

            Customer customer = Customer.login(firstName, lastName);
            if (customer != null) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
                frame.dispose();
                showCustomerActionsGUI(customer);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void showCustomerActionsGUI(Customer customer) {
        JFrame frame = new JFrame("Customer Actions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        JButton viewAccountsButton = new JButton("View Accounts");
        JButton openAccountButton = new JButton("Open New Account");
        JButton viewLoansButton = new JButton("View Loans");
        JButton availLoanButton = new JButton("Avail New Loan");
        JButton savingsReportButton = new JButton("View Annual Savings Report");
        JButton payLoanButton = new JButton("Pay Loan");

        viewAccountsButton.addActionListener(e -> 
            JOptionPane.showMessageDialog(frame, "Viewing accounts for: " + customer.getCustomer_first_name())
        );

        openAccountButton.addActionListener(e -> 
            JOptionPane.showMessageDialog(frame, "Opening new account...")
        );

        viewLoansButton.addActionListener(e -> 
            JOptionPane.showMessageDialog(frame, "Viewing loans...")
        );

        availLoanButton.addActionListener(e -> 
            JOptionPane.showMessageDialog(frame, "Availing new loan...")
        );

        savingsReportButton.addActionListener(e -> 
            JOptionPane.showMessageDialog(frame, "Generating annual savings report...")
        );

        payLoanButton.addActionListener(e -> 
            JOptionPane.showMessageDialog(frame, "Processing loan payment...")
        );

        panel.add(viewAccountsButton);
        panel.add(openAccountButton);
        panel.add(viewLoansButton);
        panel.add(availLoanButton);
        panel.add(savingsReportButton);
        panel.add(payLoanButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
