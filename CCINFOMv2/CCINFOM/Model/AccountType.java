package Model;

import HelperClass.UserInput;

import java.sql.*;

public class AccountType {

    public static void showAccountTypes(){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/dbapp_bankdb",
                    "root",
                    "1234"
            );

            String getInfoQuery = "SELECT * FROM account_type";
            PreparedStatement preparedStatementInfo = connection.prepareStatement(getInfoQuery);
            ResultSet res = preparedStatementInfo.executeQuery();

            int option = 1;
            String[] types = new String[3];
            while(res.next()){
                System.out.println(option + " - " + res.getString("account_type"));
                types[option - 1] = res.getString("account_type");
                option++;
            }

            int chosenOption;
            do {
                System.out.print("Choose account type to view information: ");
                chosenOption = Integer.parseInt(UserInput.getScanner().nextLine());
            } while( chosenOption < 1 || chosenOption > 3);


            String viewQuery = "SELECT * FROM account_type WHERE account_type = ?";

            try(PreparedStatement statement = connection.prepareStatement(viewQuery)){
                statement.setString(1, types[chosenOption - 1]);

                ResultSet typeRes = statement.executeQuery();

                if(typeRes.next()){
                    System.out.println("Account Type: " + typeRes.getString("account_type"));
                    System.out.println("Interest rate: " + typeRes.getDouble("interest_rate"));
                    System.out.println("Minimum balance: " + typeRes.getDouble("min_balance"));

                    int typeAction;
                    do {
                        System.out.println("\n1 - Change interest rate\n2 - Change minimum balance");
                        System.out.print("Choose option: ");
                        typeAction = Integer.parseInt(UserInput.getScanner().nextLine());
                    } while(typeAction < 1 || typeAction > 2);

                    switch (typeAction) {
                        case 1:
                            changeInterestRate(types[chosenOption - 1]);
                            break;
                        case 2:
                            changeMinimumBalance(types[chosenOption - 1]);
                            break;
                    }

                } else {
                    System.out.println("Account type doesn't exist");
                }
            }


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void changeInterestRate(String accType){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/dbapp_bankdb",
                    "root",
                    "1234"
            );

            System.out.print("Enter new interest rate: ");
            double rate = Double.parseDouble(UserInput.getScanner().nextLine());

            String updateQuery = "UPDATE account_type SET interest_rate = ? WHERE account_type = ?;";

            try (PreparedStatement statement = connection.prepareStatement(updateQuery)){
                statement.setDouble(1, rate);
                statement.setString(2, accType);
                int rowsAffected =  statement.executeUpdate();

                if(rowsAffected > 0){
                    System.out.println("Updated interest rate");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeMinimumBalance(String accType){
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/dbapp_bankdb",
                    "root",
                    "1234"
            );

            System.out.print("Enter new  minimum balance: ");
            double min = Double.parseDouble(UserInput.getScanner().nextLine());

            String updateQuery = "UPDATE account_type SET min_balance = ? WHERE account_type = ?;";

            try (PreparedStatement statement = connection.prepareStatement(updateQuery)){
                statement.setDouble(1, min);
                statement.setString(2, accType);
                int rowsAffected =  statement.executeUpdate();

                if(rowsAffected > 0){
                    System.out.println("Updated minimum balance");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addDefaultAccountTypes(){
        try {
        Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/dbapp_bankdb",
                    "root",
                    "1234"
        );

        String check1 = "SELECT * from account_type WHERE account_type = 'Personal'";
        String check2 = "SELECT * from account_type WHERE account_type = 'Business'";
        String check3 = "SELECT * from account_type WHERE account_type = 'Special'";

        Statement statement1 = connection.createStatement();
        Statement statement2 = connection.createStatement();
        Statement statement3 = connection.createStatement();

        ResultSet results1 = statement1.executeQuery(check1);
        ResultSet results2 = statement2.executeQuery(check2);
        ResultSet results3 = statement3.executeQuery(check3);

        if(!results1.next()) {
            String Personal = "INSERT INTO account_type (account_type, interest_rate, min_balance) " +
                    "VALUES ('Personal', 1.5, 5000)";
            statement1.executeUpdate(Personal);
        }

        if(!results2.next()) {
            String Business = "INSERT INTO account_type (account_type, interest_rate, min_balance) " +
                    "VALUES ('Business', 0.8, 30000)";
            statement2.executeUpdate(Business);
        }

        if(!results3.next()) {
            String Special = "INSERT INTO account_type (account_type, interest_rate, min_balance) " +
                    "VALUES ('Special', 2.5, 50000)";
            statement3.executeUpdate(Special);
        }

    } catch(SQLException e){
        e.printStackTrace();
    }}

}
