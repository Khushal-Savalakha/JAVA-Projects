
/**
 * This is the main application file for the BookMyShow project. It manages the overall
 * functionality of the cinema booking system, including initializing the application, 
 * managing user inputs, and coordinating between different components such as movie search, 
 * ticket booking, customer management, and payment processing.
 * 
 * The class serves as the entry point of the application, orchestrating the various features 
 * and ensuring a seamless user experience.
 * 
 * @version 1.0
 * @since 2023-08-06
 * @author Khushal Savalakha
 */

import java.sql.*;
import java.util.Scanner;

class BookMyShow {
    // ANSI escape codes for text formatting
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String RED = "\u001B[91m";
    public static final String GREEN = "\u001B[92m";

    public static MovieSearchMenu menu = new MovieSearchMenu();
    static Scanner scanner = new Scanner(System.in);
    static String mobileNumber = "-";
    public static String upiId;
    static String email = "-";
    static String customerName;
    static String password;
    static Connection connection;
    static Statement statement;
    public static int customerId = 0;
    public static int paymentFlag = 0;

    public static void main(String[] args) throws Exception {
        processAccount();
    }

    public static void processAccount() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/Book_my_show";
        String dbUsername = "user";
        String dbPassword = "7680";

        // Register the PostgreSQL JDBC driver
        Class.forName("org.postgresql.Driver");

        // Establish the database connection
        connection = DriverManager.getConnection(url, dbUsername, dbPassword);

        if (connection != null) {
            System.out.println(GREEN + "Connected to cimadetails & movies_details!" + RESET);
        } else {
            System.out.println(RED + "Connection unsuccessful" + RESET);
        }

        statement = connection.createStatement();

        String strongPassword = "Strong@123";
        createAccount();
    }

    static void createAccount() {
        boolean dataEntered = true;
        while (dataEntered) {
            try {
                System.out.println(BOLD + "1] Sign up with Gmail." + RESET);
                System.out.println(BOLD + "2] Sign up with Mobile Number." + RESET);
                System.out.println(BOLD + "3] Sign in" + RESET);
                System.out.println(BOLD + "4] Exit" + RESET);
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1: {
                        boolean validInput = true;
                        while (validInput) {
                            System.out.println("Enter your name:");
                            customerName = scanner.next();
                            System.out.print("Enter your Email:");
                            email = scanner.next();

                            if (!isValidGmailAddress(email)) {
                                System.out.println(RED + "Enter a valid Gmail address." + RESET);
                            } else {
                                validInput = false;
                            }

                            boolean validPassword = true;
                            while (validPassword) {
                                System.out.print("Enter your password:");
                                password = scanner.next();

                                if (isStrongPassword(password)) {
                                    System.out.println(GREEN + "Password is strong." + RESET);
                                    validPassword = false;
                                } else {
                                    System.out.println(RED + "Password is weak." + RESET);
                                }
                            }

                            String sql = "INSERT INTO customer(name, email, mobile_no, login_date_time, password) VALUES (?, ?, ?, now(), ?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(sql);

                            preparedStatement.setString(1, customerName);
                            preparedStatement.setString(2, email);
                            preparedStatement.setString(3, mobileNumber);
                            preparedStatement.setString(4, password);

                            int result = preparedStatement.executeUpdate();
                            if (result > 0) {
                                Statement statement = connection.createStatement();
                                System.out.println(GREEN + "Sign up successful." + RESET);
                                sql = "SELECT customer_id from customer where email = '" + email
                                        + "' and password = '" + password + "'";
                                ResultSet resultSet = statement.executeQuery(sql);
                                while (resultSet.next()) {
                                    customerId = resultSet.getInt(1);
                                    System.out.println("Customer ID:" + customerId);
                                }
                            } else {
                                System.out.println(RED + "Sign up failed." + RESET);
                            }
                        }

                        dataEntered = false;
                        break;
                    }

                    case 2: {
                        boolean validInput = true;
                        while (validInput) {
                            System.out.println("Enter your name:");
                            customerName = scanner.next();
                            System.out.print("Enter your Contact no:");
                            mobileNumber = scanner.next();

                            if (!isValidMobileNumber(mobileNumber)) {
                                System.out.println(RED + "Enter a valid mobile number." + RESET);
                            } else {
                                validInput = false;
                            }

                            boolean validPassword = true;
                            while (validPassword) {
                                System.out.print("Enter your password:");
                                password = scanner.next();

                                if (isStrongPassword(password)) {
                                    System.out.println(GREEN + "Password is strong." + RESET);
                                    validPassword = false;
                                } else {
                                    System.out.println(RED + "Password is weak." + RESET);
                                }
                            }

                            String sql = "INSERT INTO customer(name, email, mobile_no, login_date_time, password) VALUES (?, ?, ?, now(), ?)";
                            PreparedStatement preparedStatement = connection.prepareStatement(sql);

                            preparedStatement.setString(1, customerName);
                            preparedStatement.setString(2, email);
                            preparedStatement.setString(3, mobileNumber);
                            preparedStatement.setString(4, password);

                            int result = preparedStatement.executeUpdate();
                            if (result > 0) {
                                Statement statement = connection.createStatement();
                                System.out.println(GREEN + "Sign up successful." + RESET);
                                sql = "SELECT customer_id from customer where mobile_no = '" + mobileNumber
                                        + "' and password = '" + password + "'";
                                ResultSet resultSet = statement.executeQuery(sql);
                                while (resultSet.next()) {
                                    customerId = resultSet.getInt(1);
                                    System.out.println("Customer ID:" + customerId);
                                }
                            } else {
                                System.out.println(RED + "Sign up failed." + RESET);
                            }
                        }

                        dataEntered = false;
                        break;
                    }

                    case 3: {
                        System.out.println("Sign in in progress.");
                        isCustomerHasAccount();
                        dataEntered = false;
                        break;
                    }

                    case 4: {
                        dataEntered = false;
                        break;
                    }

                    default: {
                        System.out.println(RED + "Enter a valid option!" + RESET);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        menu.selectCity();
    }

    static void isCustomerHasAccount() throws SQLException {
        System.out.println("Sign in with Gmail [1] or Mobile Number [2]:");
        int choice = scanner.nextInt();
        if (choice == 1) {

            System.out.print("Enter your Email:");
            email = scanner.next();
            System.out.println("Enter your password:");
            password = scanner.next();

            Statement statement = connection.createStatement();

            String sql = "SELECT customer_id from customer where email = '" + email
                    + "' and password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                customerId = resultSet.getInt(1);
                System.out.println("Customer ID:" + customerId);
            }
        } else if (choice == 2) {

            System.out.print("Enter your Contact no:");
            mobileNumber = scanner.next();
            System.out.println("Enter your password:");
            password = scanner.next();
            Statement statement = connection.createStatement();
            String sql = "SELECT customer_id from customer where mobile_no = '" + mobileNumber
                    + "' and password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                customerId = resultSet.getInt(1);
                System.out.println("Customer ID:" + customerId);
            }
        }
    }

    static boolean isValidMobileNumber(String mobileNumber) {
        int count = 0;
        boolean isValidMN = false;
        do {
            if (mobileNumber.length() == 10) {
                for (int m = 0; m < mobileNumber.length(); m++) {
                    if (m == 0) {
                        if (mobileNumber.charAt(m) >= '7' && mobileNumber.charAt(m) <= '9') {
                            count++;
                            continue;
                        } else {
                            System.out.println(RED + "First digit should not be " + mobileNumber.charAt(m) +
                                    "\nIt must be between [7-9].\n And rest between [0-9]." + RESET);
                            count = 0;
                            break;
                        }
                    } else {
                        if (mobileNumber.charAt(m) >= '0' && mobileNumber.charAt(m) <= '9') {
                            count++;
                            continue;
                        } else {
                            System.out.println(RED + "Enter digits between [0-9]" + RESET);
                            count = 0;
                            break;
                        }
                    }
                }
                if (count == 10) {
                    System.out.println(GREEN + "Number Added successfully." + RESET);
                    isValidMN = true;
                }
            } else {
                System.out.println(RED + "Enter 10 digits." + RESET);
                return false;
            }
        } while (!isValidMN);

        return isValidMN;
    }

    public static boolean isValidGmailAddress(String email) {
        if (email == null || email.isEmpty()) {
            return false; // Email cannot be null or empty
        }

        // Split the email into local and domain parts
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false; // Email should have a single "@" symbol
        }

        String localPart = parts[0];
        String domainPart = parts[1];

        // Check local part (before "@")
        if (localPart.isEmpty()) {
            return false; // Local part should not be empty or exceed 64 characters
        }

        // Check domain part (after "@")
        if (!domainPart.equals("gmail.com")) {
            return false; // Only "gmail.com" domain is allowed
        }

        return true; // Passed all checks, it's a valid Gmail address
    }

    public static void getUPI() {
        int count = 0;
        // UPI (typically username@bankname).
        boolean isValidUpi = false;
        do {
            System.out.print("Enter UPI ID: ");
            upiId = scanner.next();
            int flag = upiId.indexOf("@");
            if (upiId.equalsIgnoreCase("back")) {
                return;
            }
            if (flag != -1) {
                if (upiId.endsWith("oksbi") || upiId.endsWith("ybl") || upiId.endsWith("okaxis")
                        || upiId.endsWith("okhdfcbank")) {
                    System.out.println(GREEN + "Valid UPI ID" + RESET);
                    isValidUpi = true;
                    paymentFlag = 9;
                } else {
                    System.out.println(RED + "Invalid bank name\nUPI (typically username@bankname)." + RESET);
                }
            } else {
                System.out.println(RED + "Invalid" + RESET);
            }
        } while (!isValidUpi);
    }

    public static boolean isStrongPassword(String password) {
        if (password.length() < 8) {
            return false; // Password is too short for a strong password
        }

        boolean hasDigit = false;
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasSpecialChar = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if ("@#$%^&+=!".indexOf(ch) != -1) {
                hasSpecialChar = true;
            }
        }

        return hasDigit && hasLowercase && hasUppercase && hasSpecialChar;
    }
}
