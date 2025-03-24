package View;

import Model.AvailedLoans;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AvailedLoansGUI extends JFrame implements ActionListener {

    private JTextField customerIdField, loanIdField;
    private JButton viewLoansButton, applyLoanButton, payLoanButton;

    public AvailedLoansGUI() {
        setTitle("Availed Loans Management");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Customer ID:"));
        customerIdField = new JTextField();
        panel.add(customerIdField);

        viewLoansButton = new JButton("View Loans");
        viewLoansButton.addActionListener(this);
        panel.add(viewLoansButton);

        applyLoanButton = new JButton("Apply for Loan");
        applyLoanButton.addActionListener(this);
        panel.add(applyLoanButton);

        panel.add(new JLabel("Loan ID (For Payment):"));
        loanIdField = new JTextField();
        panel.add(loanIdField);

        payLoanButton = new JButton("Pay Loan");
        payLoanButton.addActionListener(this);
        panel.add(payLoanButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int customerId = Integer.parseInt(customerIdField.getText());

            if (e.getSource() == viewLoansButton) {
                AvailedLoans.showAvailedLoans(customerId);
            } else if (e.getSource() == applyLoanButton) {
                AvailedLoans.loanAppli(customerId);
            } else if (e.getSource() == payLoanButton) {
                int loanId = Integer.parseInt(loanIdField.getText());
                AvailedLoans.loanPayment(customerId);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for IDs.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AvailedLoansGUI();
    }
}

