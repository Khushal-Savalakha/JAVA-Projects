
/**
 * This class manages the ticket booking process within the BookMyShow application.
 * It handles seat selection, ticket availability checks, and payment processing.
 * Users can select their desired movie, choose available seats, and proceed to payment.
 * The class ensures that the booking is confirmed and stored in the system once the
 * payment is successfully completed.
 * 
 * This class is integral to the application's core functionality, providing users 
 * with an intuitive and efficient way to book movie tickets.
 * 
 * @version 1.0
 * @since 2023-08-06
 * @author Khushal Savalakha
 */

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class TicketBooking1 {
    static BookMyShow payment = new BookMyShow();
    static Connection con;
    static Statement st;
    static int count = 0;
    static ResultSet rs;
    static int i = 0;
    static String sql1;
    static int k;
    static String x = "--";
    static ResultSet rs1;
    static Scanner sc = new Scanner(System.in);
    static int totalDesiredSeats;
    static String totalDesiredSeatsWithNumber;
    static int[] seatIntegers;
    static int notAvailable;
    static int a, b;
    static int bookedSeatNumber;
    static String sql3 = "select * from cinemaseats where sr_no=" + bookedSeatNumber + "";
    static String desiredSeatDetails = "";
    static String seatNumbers[];
    static int notBookedFlag = 0;
    static String selectedMovie;
    static int selectedCinemaId;
    static String selectedCinemaName;
    static String selectedCinemaAddress;
    static String selectedShowTime;
    static int selectedScreen;
    static String selectedRunningTime;
    static String selectedLanguage;
    static int selectedMoviePrice;
    static String movieReleaseDate;
    static String userDate;
    static int seats;

    public static void main(String[] args) {
        try {
            CinemaSeatsDatabase("Inception", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void CinemaSeatsDatabase(String movie, int cinemaId) throws Exception {
        try {
            selectedMovie = movie;
            selectedCinemaId = cinemaId;
            String url = "jdbc:postgresql://localhost:5432/Book_my_show";
            String username = "user";
            String password = "7680";

            // Register the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the database connection
            con = DriverManager.getConnection(url, username, password);

            if (con != null) {
                System.out.println("Connected to cinemaseats database!");
            } else {
                System.out.println("Unsuccessful");
            }
            // Step 3: Create a statement and write SQL Query
            st = con.createStatement();
            String sql = "select cinema_name,address from cinema_details where cinema_id =" + selectedCinemaId + "";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                selectedCinemaName = rs.getString("cinema_name");
                selectedCinemaAddress = rs.getString("address");
                System.out.println("Cinema data Fetched");
                // Do NOT close the ResultSet here
            }

            sql = "select showtime,screen,running_time,language,price,release_date from movies_details where cinema_id ="
                    + selectedCinemaId + " and movie_title like '%" + selectedMovie + "%'";

            // System.out.println("SQL Query: " + sql);
            rs = st.executeQuery(sql);
            while (rs.next()) {
                selectedShowTime = rs.getString("showtime");
                selectedScreen = rs.getInt("screen");
                selectedRunningTime = rs.getString("running_time");
                selectedLanguage = rs.getString("language");
                String price = rs.getString("price").trim(); // Trim to remove leading/trailing spaces
                price = price.replaceAll("[^0-9]", ""); // Remove non-numeric characters
                selectedMoviePrice = Integer.parseInt(price);
                movieReleaseDate = rs.getString("release_date");
                // Do NOT close the ResultSet here
            }
            System.out.println("Your show release date is " + movieReleaseDate + "");

            while (true) {
                System.out.print("Write your preferred show date [show date>release date (Format: 2023-08-09)]: ");
                userDate = sc.nextLine();

                if (userDate.isEmpty()) {
                    System.out.println("Please enter a date.");
                } else {
                    try {
                        // Extract last two digits from userDate
                        int userDay = Integer.parseInt(userDate.substring(8));

                        // Extract last two digits from movieReleaseDate
                        int releaseDay = Integer.parseInt(movieReleaseDate.substring(8));

                        // Compare the last two digits
                        if (userDay < releaseDay) {
                            System.out.println(
                                    "\u001B[91mPlease Enter a Valid date (date should be after the release date)!\u001B[0m");
                        } else {
                            System.out.println("\u001B[92mValid date.\u001B[0m");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("\u001B[91mInvalid date format. Please use the format: 2023-08-09\u001B[0m");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Steps();
    }

    static public void Steps() {
        TicketShowing();
        SeatIsAvailable();
    }

    static public void TicketShowing() {
        try {
            String sql1;
            for (i = 0; i < 24; i++) {
                if (i % 6 == 0) {
                    count = count + 1;
                    System.out.println();
                }
                sql1 = "select screen_" + selectedScreen + " from cinemaseats where sr_no=" + i + "";
                rs = st.executeQuery(sql1);
                while (rs.next()) {
                    k = rs.getInt("screen_" + selectedScreen);// here two means second column
                    if (k == 0) {
                        System.out.print("\u001B[32m[" + i / 6 + "" + i % 6 + "]\u001B[0m"); // Green for available
                    } else {
                        System.out.print("\u001B[91m[" + x + "]\u001B[0m"); // Red color for occupied seats
                    }
                }
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void SeatIsAvailable() {
        boolean main = true;
        int totalDesiredSeats;
        String totalDesiredSeatsWithNumbers;
        while (main) {
            try {
                System.out.print("Enter how many seats you want to book: ");
                totalDesiredSeats = sc.nextInt();
                seats = totalDesiredSeats;
                sc.nextLine(); // Consume the newline character left by sc.nextInt()
                if (totalDesiredSeats == 0) {
                    SeatIsAvailable();
                    return;
                }
                System.out.print("Write seat numbers with spaces you want to buy: ");
                totalDesiredSeatsWithNumbers = sc.nextLine();

                seatNumbers = totalDesiredSeatsWithNumbers.split(" ");

                for (int i = 0; i < seatNumbers.length; i++) {
                    desiredSeatDetails = desiredSeatDetails.concat("" + seatNumbers[i]);

                    int a = Character.getNumericValue(seatNumbers[i].charAt(0)); // Convert '1' to 1
                    int b = Character.getNumericValue(seatNumbers[i].charAt(1)); // Convert '2' to 2
                    bookedSeatNumber = 6 * a + b;
                    sql3 = "select screen_" + selectedScreen + " from cinemaseats where sr_no=" + bookedSeatNumber + "";
                    rs1 = st.executeQuery(sql3);

                    if (rs1.next()) { // Move cursor to the first row of the result set
                        int mainResult = rs1.getInt("screen_" + selectedScreen);

                        if (mainResult == 1) {
                            notAvailable = notAvailable + 1;
                            desiredSeatDetails = desiredSeatDetails.concat(" is not available \t");
                        } else {
                            desiredSeatDetails = desiredSeatDetails.concat(" is available \t");
                        }
                    }
                }
                if (notAvailable > 0) {
                    System.out.println("\u001B[91m" + desiredSeatDetails + "\u001B[0m"); // Red color for unavailable
                                                                                         // seats
                    boolean b = true;
                    while (b) {
                        System.out.print("Do you want to Change Ticket Number(Y) or cancel the ticket Booking(Y1): ");
                        String ans = sc.nextLine().toUpperCase();

                        if (ans.equals("Y")) { // Use .equals() to compare string content
                            desiredSeatDetails = "";
                            notAvailable = 0;
                            Steps();
                            main = false;
                            b = false;
                        } else if (ans.equals("Y1")) { // Use .equals() to compare string content
                            System.out.println("Booking process is canceled.");
                            desiredSeatDetails = "";
                            notAvailable = 0;
                            b = false;
                            main = false;
                        } else {
                            System.out.println("Please enter a valid input.");
                        }
                    }
                } else {
                    BookTickets();
                    if (notBookedFlag == 1) {
                        System.out.println("Sorry for your problem.");
                        TicketShowing();
                    } else {
                        // Close the resources
                        rs1.close();
                        st.close();
                        con.close();
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Write details properly !");
                sc.nextLine();
            }
        }
    }

    static public void BookTickets() {
        System.out.println();
        String sql2;
        int c = 0;

        try {
            int pay = 0;
            int moneyTransferred = 0;
            int totalAmount = 0;
            for (int i = 0; i < seatNumbers.length; i++) {
                if (pay == 0) {
                    payment.getUPI();
                    pay = 1;
                    if (payment.paymentFlag == 9) {
                        totalAmount = seats * selectedMoviePrice;
                        String sql = "Insert into payment(customer_id,ticket_booked,cinema_id,screen,amount,show_date) values("
                                + payment.customerId + "," + seats + "," + selectedCinemaId + ","
                                + selectedScreen + "," + totalAmount + "," + userDate + ");";
                        Statement st = con.createStatement();
                        moneyTransferred = st.executeUpdate(sql);
                        if (moneyTransferred > 0) {
                            System.out.println("Payment Successful ");
                            System.out.println("Your " + seats + " seat(s) are booked.");
                            notBookedFlag = 0;
                        } else {
                            System.out.println("Seats are not booked.");
                        }
                    }
                }
                if (moneyTransferred == 1) {
                    desiredSeatDetails = desiredSeatDetails.concat("" + seatNumbers[i]);
                    int a = Character.getNumericValue(seatNumbers[i].charAt(0)); // Convert '1' to 1
                    int b = Character.getNumericValue(seatNumbers[i].charAt(1)); // Convert '2' to 2
                    bookedSeatNumber = 6 * a + b;
                    sql2 = "update cinemaseats set screen_" + selectedScreen + "=1 where sr_no=" + bookedSeatNumber
                            + "";
                    c = c + st.executeUpdate(sql2);
                } else {
                    System.out.println("Please complete the payment process!");
                }
            }
            if (c == seatNumbers.length) {
                payment.paymentFlag = 0;
                moneyTransferred = 0;
                System.out.println("Cinema name: " + selectedCinemaName);
                System.out.println("Movie Title: " + selectedMovie);
                System.out.println("Movie Language: " + selectedLanguage);
                System.out.println("Number of Tickets: " + seats);
                System.out.println("Total Payment: " + totalAmount);
                System.out.println("Show date: " + userDate);
                System.out.println("Show time: " + selectedShowTime);
                TicketShowing(); // Display updated seating information here
            } else {
                System.out.println("Seats are not booked.");
                notBookedFlag = 1;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
