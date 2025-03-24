package View;

import Model.InterestCalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterestCalculatorGUI extends JFrame implements ActionListener {

    private JTextField accountIdField;
    private JButton calculateButton;

    public InterestCalculatorGUI() {
        setTitle("Interest Calculator");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        panel.add(new JLabel("Enter Account ID:"));
        accountIdField = new JTextField();
        panel.add(accountIdField);

        calculateButton = new JButton("Calculate Interest");
        calculateButton.addActionListener(this);
        panel.add(calculateButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int accountId = Integer.parseInt(accountIdField.getText());
            InterestCalculator interestCalculator = new InterestCalculator();
            interestCalculator.calculateInterest(accountId);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Account ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new InterestCalculatorGUI();
    }
}

