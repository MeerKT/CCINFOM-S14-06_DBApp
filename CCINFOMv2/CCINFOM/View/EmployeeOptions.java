package View;

import HelperClass.UserInput;
import Model.AccountType;
import Model.LoanOptions;
import Model.TransactionHistory;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        viewCustomersButton.addActionListener(e -> EmployeeOptions.showCustomersOfBank());
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
    public static void showCustomersOfBank(){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/dbapp_bankdb",
                    "root",
                    "1234"
            );

            String getInfoQuery = "SELECT * FROM customer_records";
            PreparedStatement preparedStatementInfo = connection.prepareStatement(getInfoQuery);
            ResultSet res = preparedStatementInfo.executeQuery();

            int option = 1;
            String[] types = new String[3];
            while(res.next()){
                System.out.println(option + " - " + res.getString("customer_records"));
                types[option - 1] = res.getString("customer_records");
                option++;
            }

            int chosenOption;
            do {
                System.out.print("Choose cutomer record to view information: ");
                chosenOption = Integer.parseInt(UserInput.getScanner().nextLine());
            } while( chosenOption < 1 || chosenOption > option -1);


            String viewQuery = "SELECT * FROM customer_records WHERE customer_records = ?";

            try(PreparedStatement statement = connection.prepareStatement(viewQuery)){
                statement.setString(1, types[chosenOption - 1]);

                ResultSet typeRes = statement.executeQuery();

                if(typeRes.next()){
                    System.out.println("Customer_ID: " + typeRes.getString("customer_ID"));
                    System.out.println("First Name: " + typeRes.getDouble("first_name"));
                    System.out.println("Last Name: " + typeRes.getDouble("last_name"));
                    System.out.println("Birth Date: " + typeRes.getString("birthdate"));
                    System.out.println("Phone No: " + typeRes.getDouble("phone_number"));
                    System.out.println("Email Address: " + typeRes.getDouble("email_address"));


                } else {
                    System.out.println("Customer record doesn't exist");
                }
            }


        } catch (SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}