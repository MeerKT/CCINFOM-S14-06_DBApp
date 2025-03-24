package View;

import Model.TransactionHistory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionHistoryGUI extends JFrame implements ActionListener {

    private JTextField yearField;
    private JButton viewTransactionsButton, viewLoanPaymentsButton;
    private JTextArea resultArea;

    public TransactionHistoryGUI() {
        setTitle("Annual Transaction and Loan Report");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 5, 5));

        inputPanel.add(new JLabel("Enter Year:"));
        yearField = new JTextField();
        inputPanel.add(yearField);

        viewTransactionsButton = new JButton("View Annual Transactions");
        viewTransactionsButton.addActionListener(this);
        inputPanel.add(viewTransactionsButton);

        viewLoanPaymentsButton = new JButton("View Annual Loan Payments");
        viewLoanPaymentsButton.addActionListener(this);
        inputPanel.add(viewLoanPaymentsButton);

        panel.add(inputPanel);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int year = Integer.parseInt(yearField.getText());
            String result;

            if (e.getSource() == viewTransactionsButton) {
                result = TransactionHistory.generateAnnualTransaction(year);
            } else if (e.getSource() == viewLoanPaymentsButton) {
                result = TransactionHistory.generateAnnualLoanPayment(year);
            } else {
                result = "Invalid action!";
            }

            resultArea.setText(result);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid year.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new TransactionHistoryGUI();
    }
}

