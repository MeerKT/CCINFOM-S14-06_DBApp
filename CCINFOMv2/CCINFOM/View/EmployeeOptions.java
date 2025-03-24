package View;

import Model.AccountType;
import Model.LoanOptions;
import Model.TransactionHistory;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class EmployeeOptions extends JFrame {
    public static void showOptions() {
        JFrame frame = new JFrame("Employee Options");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));

        JButton addLoanButton = new JButton("Add Loan Options");
        JButton viewAccountTypesButton = new JButton("View Account Types");
        JButton viewCustomersButton = new JButton("View Customers");
        JButton viewTransactionHistoryButton = new JButton("View Transaction History");
        JButton viewLoanHistoryButton = new JButton("View Loan Payment History");
        JButton viewAnnualTransactionButton = new JButton("View Annual Transaction Volume");
        JButton viewAnnualLoanButton = new JButton("View Annual Loan Payment Volume");
        
        addLoanButton.addActionListener(e -> addLoanOptions());
        viewAccountTypesButton.addActionListener(e -> AccountType.showAccountTypes());
        viewCustomersButton.addActionListener(e -> showCustomersOfBank());
        viewTransactionHistoryButton.addActionListener(e -> TransactionHistory.viewTransactionHistoryOfAccount());
        viewLoanHistoryButton.addActionListener(e -> TransactionHistory.viewLoanPaymentHistoryOfAccount());
        viewAnnualTransactionButton.addActionListener(e -> TransactionHistory.generateAnnualTransaction());
        viewAnnualLoanButton.addActionListener(e -> TransactionHistory.generateAnnualLoanPayment());
        
        panel.add(addLoanButton);
        panel.add(viewAccountTypesButton);
        panel.add(viewCustomersButton);
        panel.add(viewTransactionHistoryButton);
        panel.add(viewLoanHistoryButton);
        panel.add(viewAnnualTransactionButton);
        panel.add(viewAnnualLoanButton);
        
        frame.add(panel);
        frame.setVisible(true);
    }
    
    private static void addLoanOptions() {
        String loanType = JOptionPane.showInputDialog("Enter Loan Type:");
        String interestRateStr = JOptionPane.showInputDialog("Enter Interest Rate (decimal):");
        String loanDurStr = JOptionPane.showInputDialog("Enter Loan Duration (months):");
        String maxLoanStr = JOptionPane.showInputDialog("Enter Max Loan Price:");
        String minLoanStr = JOptionPane.showInputDialog("Enter Minimum Loan Price:");
        
        if (loanType != null && interestRateStr != null && loanDurStr != null && maxLoanStr != null && minLoanStr != null) {
            try {
                double interestRate = Double.parseDouble(interestRateStr);
                int loanDur = Integer.parseInt(loanDurStr);
                double maxLoan = Double.parseDouble(maxLoanStr);
                double minLoan = Double.parseDouble(minLoanStr);
                LoanOptions.addLoanOption(loanType, interestRate, loanDur, maxLoan, minLoan);
                JOptionPane.showMessageDialog(null, "Loan Option Added Successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid Input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void showCustomersOfBank() {
        String url = "jdbc:mysql://localhost:3306/bank_db";
        String user = "root";
        String password = "Sweetmochi*2003";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String getInfoQuery = "SELECT customer_ID, first_name, last_name FROM customer_records";
            PreparedStatement preparedStatementInfo = connection.prepareStatement(getInfoQuery);
            ResultSet res = preparedStatementInfo.executeQuery();

            List<String> customerIDs = new ArrayList<>();
            StringBuilder customerList = new StringBuilder("Select a customer:\n");

            int option = 1;
            while (res.next()) {
                String customerID = res.getString("customer_ID");
                customerList.append(option).append(" - ").append(res.getString("first_name"))
                        .append(" ").append(res.getString("last_name")).append("\n");
                customerIDs.add(customerID);
                option++;
            }

            if (customerIDs.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No customers found.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String chosenOptionStr = JOptionPane.showInputDialog(customerList.toString());
            if (chosenOptionStr == null) return;

            try {
                int chosenOption = Integer.parseInt(chosenOptionStr);
                if (chosenOption < 1 || chosenOption > customerIDs.size()) {
                    JOptionPane.showMessageDialog(null, "Invalid selection.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String selectedCustomerID = customerIDs.get(chosenOption - 1);
                String viewQuery = "SELECT * FROM customer_records WHERE customer_ID = ?";

                try (PreparedStatement statement = connection.prepareStatement(viewQuery)) {
                    statement.setString(1, selectedCustomerID);
                    ResultSet typeRes = statement.executeQuery();

                    if (typeRes.next()) {
                        String customerInfo = "Customer ID: " + typeRes.getString("customer_ID") + "\n" +
                                "First Name: " + typeRes.getString("first_name") + "\n" +
                                "Last Name: " + typeRes.getString("last_name") + "\n" +
                                "Birth Date: " + typeRes.getString("birthdate") + "\n" +
                                "Phone No: " + typeRes.getString("phone_number") + "\n" +
                                "Email Address: " + typeRes.getString("email_address");

                        JOptionPane.showMessageDialog(null, customerInfo, "Customer Details", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Customer record doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
