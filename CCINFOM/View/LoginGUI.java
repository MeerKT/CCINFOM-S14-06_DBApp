package View;

import controller.LoginController;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginGUI extends JFrame implements ActionListener {

    private JTextField userField;
    private JPasswordField passField;
    private JComboBox<String> userTypeCombo;
    private JButton loginButton;
    private final LoginController loginController;

    public LoginGUI() {
        loginController = new LoginController(this);

        setTitle("Bank System Login");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel userTypeLabel = new JLabel("User Type:");

        userField = new JTextField(15);
        passField = new JPasswordField(15);

        String[] userTypes = {"Customer", "Employee"};
        userTypeCombo = new JComboBox<>(userTypes);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        setLayout(new GridLayout(4, 2, 5, 5));

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(userTypeLabel);
        add(userTypeCombo);
        add(new JLabel()); 
        add(loginButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        String userType = (String) userTypeCombo.getSelectedItem();

        loginController.authenticateUser(username, password, userType);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginGUI().setVisible(true));
    }
}


