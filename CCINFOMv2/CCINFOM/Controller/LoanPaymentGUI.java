package Controller;

import Controller.CustomerGUI;
import Model.Customer;
import Model.Account;
import Model.AvailedLoans;
import Model.LoanOptions;
import Model.TransactionHistory;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanPaymentGUI {
    public static void showLoanPaymentGUI(int customerId) {
        JFrame frame = new JFrame("Loan Payment");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel loanIdLabel = new JLabel("Enter Loan ID:");
        JTextField loanIdField = new JTextField();
        
        JLabel accountIdLabel = new JLabel("Enter Account ID:");
        JTextField accountIdField = new JTextField();
        
        JButton payButton = new JButton("Pay Loan");
        JButton cancelButton = new JButton("Cancel");
        
        payButton.addActionListener(e -> {
            try {
                int loanId = Integer.parseInt(loanIdField.getText());
                int accountId = Integer.parseInt(accountIdField.getText());
                
                AvailedLoans.loanPayment(customerId);
                JOptionPane.showMessageDialog(frame, "Loan payment processed successfully!");
                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> frame.dispose());
        
        panel.add(loanIdLabel);
        panel.add(loanIdField);
        panel.add(accountIdLabel);
        panel.add(accountIdField);
        panel.add(payButton);
        panel.add(cancelButton);
        
        frame.add(panel);
        frame.setVisible(true);
    }
}
