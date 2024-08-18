
/**
 * This class handles the functionality of searching and displaying movie information.
 * Users can search for movies by title, description, or cinema. The search results
 * are displayed with relevant movie details, such as showtimes and ratings.
 * 
 * This class is a key component of the BookMyShow application, enabling users to 
 * easily find movies that match their preferences and locations.
 * 
 * @version 1.0
 * @since 2023-08-06
 * @author Khushal Savalakha
 */

import java.io.*;
import java.sql.*;
import java.util.*;

public class MovieSearchMenu {
    static Connection connection;
    static String sql = "";
    static String[] cityList = {
            "Ahmedabad", "Mumbai", "Delhi", "Bengaluru", "Chennai City", "Pune", "Kolkata",
            "Kochi", "Hyderabad", "Jaipur", "Agra", "Varanasi", "Amritsar", "Surat"
    };
    static Scanner scanner = new Scanner(System.in);
    static String selectedCity;
    static String movieName;
    static String movieType;
    static String cityName = "null";
    static String selectedMovie = "null";
    static int selectedCinemaId = 0;
    static TicketBooking1 ticketBooking = new TicketBooking1();

    // ANSI escape codes for text formatting
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String ITALIC = "\u001B[3m";

    // ANSI escape codes for text colors
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";

    public static void main(String[] args) throws Exception {
        selectCity();
    }

    public static void selectCity() {
        try {
            String url = "jdbc:postgresql://localhost:5432/Book_my_show";
            String username = "user";
            String password = "7680";

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);

            if (connection != null) {
                System.out.println(GREEN + "Connected to PostgreSQL database." + RESET);
            } else {
                System.out.println(RED + "Connection unsuccessful." + RESET);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                System.out.println("Select your city:");
                for (int i = 0; i < cityList.length; i++) {
                    System.out.println((i + 1) + "]" + cityList[i]);
                }
                System.out.print("Enter your city number: ");
                int citySelection = scanner.nextInt();
                selectedCity = cityList[citySelection - 1];
                System.out.println(selectedCity);
                searchMenu();
            } catch (Exception e) {
                System.out.println(RED + "Please enter data properly." + RESET);
                scanner.nextLine();
            }
        }
    }

    private static void searchMenu() {
        while (true) {
            try {
                System.out.print(
                        "Do you want to search through " + BOLD + "1) Movie Name" + RESET + ", " +
                                BOLD + "2) Area Name" + RESET + ", " +
                                BOLD + "3) Movie Type" + RESET + ", " +
                                "or " + BOLD + "4) Go to main menu" + RESET + ": ");
                int selection = scanner.nextInt();

                switch (selection) {
                    case 1: {
                        searchMovieName();
                        break;
                    }
                    case 2: {
                        searchAreaName();
                        break;
                    }
                    case 3: {
                        searchMovieType();
                        break;
                    }
                    case 4: {
                        selectCity();
                        break;
                    }
                    default: {
                        System.out.println(RED + "Please enter a valid option!" + RESET);
                    }
                }
            } catch (Exception e) {
                System.out.println(RED + "Please enter data properly." + RESET);
                scanner.nextLine();
            }
        }
    }

    private static void searchMovieName() {
        try {
            ResultSet resultSet;
            boolean isMovieContain = true;
            int flag = 0;
            while (isMovieContain) {
                flag = 0;
                System.out.print("Enter movie name: ");
                scanner.nextLine();
                movieName = scanner.nextLine();

                sql = "SELECT m.movie_title, m.Language, m.Movie_Description, m.Running_Time, m.Release_date, m.ratings,m.price FROM movies_details AS m  INNER JOIN cinema_details AS c ON m.cinema_id = c.cinema_id WHERE m.movie_title LIKE ? AND c.city_name = ? group by m.movie_title, m.Language, m.Movie_Description, m.Running_Time, m.Release_date, m.ratings,m.price;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, "%" + movieName + "%");
                preparedStatement.setString(2, selectedCity);

                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    System.out.println(BOLD + "Movie Name: " + RESET + resultSet.getString("movie_title"));
                    System.out.println(BOLD + "Movie Language: " + RESET + resultSet.getString("Language"));
                    System.out.println(BOLD + "Movie Type: " + RESET + resultSet.getString("Movie_Description"));
                    System.out.println(BOLD + "Movie Running Time: " + RESET + resultSet.getString("Running_Time"));
                    System.out.println(BOLD + "Movie Release Date: " + RESET + resultSet.getString("Release_date"));
                    System.out.println(BOLD + "Movie Rating: " + RESET + resultSet.getDouble("ratings"));
                    System.out.println(BOLD + "Movie Price: " + RESET + resultSet.getString("price"));
                    selectedMovie = resultSet.getString("movie_title");
                    flag = 1;
                }
                resultSet.close();

                if (flag == 0) {
                    System.out.println("Movie is not available!");
                }

                while (true) {
                    if (flag == 1) {
                        System.out.print(
                                "Do you want to search another movie name or go to SearchMenu (Y/BACK) or proceed further (Y1): ");
                        String ans = scanner.next();

                        if (ans.equalsIgnoreCase("Y") || ans.equalsIgnoreCase("YES")) {
                            isMovieContain = true;
                            break;
                        } else if (ans.equalsIgnoreCase("Y1")) {
                            if (!selectedMovie.equals("null") && selectedCinemaId != 0) {
                                resultSet.close();
                                System.out.println("Now your booking process is started.");
                                ticketBooking.CinemaSeatsDatabase(selectedMovie, selectedCinemaId);
                                System.out.println("Thanks for choosing us for your service");
                                selectedMovie = "null";
                                selectedCinemaId = 0;
                                searchMenu();
                            } else {
                                resultSet.close();
                                searchAreaName();
                                if (!selectedMovie.equals("null") && selectedCinemaId != 0) {
                                    resultSet.close();
                                    System.out.println("Now your booking process is started.");
                                    ticketBooking.CinemaSeatsDatabase(selectedMovie, selectedCinemaId);
                                    System.out.println("Thanks for choosing us for your service");
                                    selectedMovie = "null";
                                    selectedCinemaId = 0;
                                    searchMenu();
                                }
                            }
                            isMovieContain = false;
                            break;
                        } else if (ans.equalsIgnoreCase("BACK") || ans.equalsIgnoreCase("Exit")) {
                            searchMenu();
                            isMovieContain = false;
                            break;
                        }
                    } else {
                        System.out.print(
                                "Do you want to search another movie name or go to SearchMenu (Y/BACK): ");
                        String ans = scanner.next();

                        if (ans.equalsIgnoreCase("Y") || ans.equalsIgnoreCase("YES")) {
                            isMovieContain = true;
                            break;
                        } else if (ans.equalsIgnoreCase("BACK") || ans.equalsIgnoreCase("Exit")) {
                            searchMenu();
                            isMovieContain = false;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void searchAreaName() {
        boolean isAreaContains = true;
        int flag = 0;
        int selectionAnswer;

        while (isAreaContains) {
            try {
                System.out.println("Enter your Area Name:");
                scanner.nextLine();
                String areaName = scanner.nextLine();
                sql = "SELECT Cinema_Id, Cinema_Name, Address FROM cinema_details WHERE Address LIKE '%" + areaName
                        + "%' AND City_Name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, selectedCity);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    System.out.println();
                    System.out.println(BOLD + "Cinema Id: " + RESET + resultSet.getInt(1) + "  " + BOLD
                            + "Cinema Name: " + RESET + resultSet.getString(2)
                            + "  " + BOLD + "Cinema Address: " + RESET + resultSet.getString(3));
                    System.out.println();
                    flag = 1;
                }

                if (flag == 0) {
                    System.out.println(RED + "Write area name properly !" + RESET);
                }

                while (true) {
                    if (flag == 1) {
                        System.out.print(
                                "Do you want to search another Area/address or go to SearchMenu (Y/BACK) or proceed further (Y1): ");
                        String ans2 = scanner.next();

                        if (ans2.equalsIgnoreCase("Y1")) {
                            System.out.print("Enter Cinema Id In which you want to see a movie: ");
                            selectedCinemaId = scanner.nextInt();
                            if (!selectedMovie.equals("null") && selectedCinemaId != 0) {
                                System.out.println("Now your booking process is started.");
                                ticketBooking.CinemaSeatsDatabase(selectedMovie, selectedCinemaId);
                                System.out.println("Thanks for choosing us for your service");
                                selectedMovie = "null";
                                selectedCinemaId = 0;
                                searchMenu();
                                isAreaContains = false;
                            } else {
                                System.out.print("Do you want to search Movie[1] or Movie_type[2]: ");
                                selectionAnswer = scanner.nextInt();

                                switch (selectionAnswer) {
                                    case 1:
                                        searchMovieName();
                                        break;
                                    case 2:
                                        searchMovieType();
                                        break;
                                    default:
                                        System.out.println(RED + "Enter a valid number !" + RESET);
                                }
                                if (!selectedMovie.equals("null") && selectedCinemaId != 0) {
                                    System.out.println("Now your booking process is started.");
                                    ticketBooking.CinemaSeatsDatabase(selectedMovie, selectedCinemaId);
                                    System.out.println("Thanks for choosing us for your service");
                                    selectedMovie = "null";
                                    selectedCinemaId = 0;
                                    searchMenu();
                                }
                            }
                            break;
                        } else if (ans2.equalsIgnoreCase("Y") || ans2.equalsIgnoreCase("YES")) {
                            break;
                        } else if (ans2.equalsIgnoreCase("BACK") || ans2.equalsIgnoreCase("Exit")) {
                            searchMenu();
                            isAreaContains = false;
                            break;
                        }
                    } else {
                        System.out.print(
                                "Do you want to search another Area/address or go to SearchMenu (Y/BACK): ");
                        String ans2 = scanner.next();
                        if (ans2.equalsIgnoreCase("Y") || ans2.equalsIgnoreCase("YES")) {
                            break;
                        } else if (ans2.equalsIgnoreCase("BACK") || ans2.equalsIgnoreCase("Exit")) {
                            searchMenu();
                            isAreaContains = false;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void searchMovieType() {
        int count = 0;
        String[] movieDetails = new String[8];
        try {
            int flag = 0;
            boolean isMovieTypeContains = true;
            while (isMovieTypeContains) {
                System.out.println("Enter movie type which you want to see:");
                scanner.nextLine();
                movieType = scanner.nextLine();

                sql = "SELECT m.movie_title, m.Language, m.Movie_Description, m.Running_Time, m.Release_date, m.ratings,m.price FROM movies_details AS m INNER JOIN cinema_details AS c ON m.cinema_id = c.cinema_id WHERE m.Movie_Description LIKE ? AND c.city_name = ? group by  m.Movie_Description,m.movie_title, m.Language,m.Running_Time, m.Release_date, m.ratings,m.price;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, "%" + movieType + "%");
                preparedStatement.setString(2, selectedCity);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    System.out.print((count + 1) + "]");
                    System.out.println(BOLD + "Movie Name :" + RESET + resultSet.getString("movie_Title"));
                    System.out.println(BOLD + "Movie language :" + RESET + resultSet.getString("LANGUAGE"));
                    System.out.println(BOLD + "Movie type :" + RESET + resultSet.getString("movie_description"));
                    System.out.println(BOLD + "Movie Running Time :" + RESET + resultSet.getString("Running_Time"));
                    System.out.println(BOLD + "Movie Release date :" + RESET + resultSet.getString("Release_date"));
                    System.out.println(BOLD + "Movie rating :" + RESET + resultSet.getDouble("ratings"));
                    System.out.println(BOLD + "Movie Price :" + RESET + resultSet.getString("price"));
                    movieDetails[count] = resultSet.getString(1);
                    ++count;
                    System.out.println();
                    flag = 1;
                }

                if (flag == 0) {
                    System.out.println("That type of movie is not available");
                }

                while (true) {
                    if (flag == 1) {
                        System.out.print(
                                "Do you want to search another Movie Type  or go to SearchMenu(Y/BACK) or proceed further(Y1): ");
                        String ans2 = scanner.next();
                        if (ans2.equalsIgnoreCase("Y1")) {
                            System.out.print("Enter Movie Number which you want to see: ");
                            int movieNumber = scanner.nextInt();
                            selectedMovie = movieDetails[movieNumber - 1];

                            if (!selectedMovie.equals("null") && selectedCinemaId != 0) {
                                resultSet.close();
                                System.out.println("Now your booking process is started.");
                                ticketBooking.CinemaSeatsDatabase(selectedMovie, selectedCinemaId);
                                System.out.println("Thanks for choosing us for your service");
                                selectedMovie = "null";
                                selectedCinemaId = 0;
                                searchMenu();
                            } else {
                                resultSet.close();
                                searchAreaName();
                                if (!selectedMovie.equals("null") && selectedCinemaId != 0) {
                                    resultSet.close();
                                    System.out.println(
                                            "                            Now your booking process is started.");
                                    ticketBooking.CinemaSeatsDatabase(selectedMovie, selectedCinemaId);
                                    System.out.println("Thanks for choosing us for your service");
                                    selectedMovie = "null";
                                    selectedCinemaId = 0;
                                    searchMenu();
                                }
                            }
                            isMovieTypeContains = false;
                            break;
                        } else if (ans2.equalsIgnoreCase("Y") || ans2.equalsIgnoreCase("YES")) {
                            isMovieTypeContains = true;
                            break;
                        } else if (ans2.equalsIgnoreCase("BACK") || ans2.equalsIgnoreCase("Exit")) {
                            searchMenu();
                            isMovieTypeContains = false;
                            break;
                        }
                    } else {
                        System.out.print(
                                "Do you want to search another Movie Type  or go to SearchMenu(Y/BACK): ");
                        String ans2 = scanner.next();
                        if (ans2.equalsIgnoreCase("Y") || ans2.equalsIgnoreCase("YES")) {
                            isMovieTypeContains = true;
                            break;
                        } else if (ans2.equalsIgnoreCase("BACK") || ans2.equalsIgnoreCase("Exit")) {
                            searchMenu();
                            isMovieTypeContains = false;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
