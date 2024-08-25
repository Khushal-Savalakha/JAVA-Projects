
/**
 * SuperApp - A comprehensive application that integrates MyPay and BookMyShow services.
 * 
 * Author:
 * Khushal Savalakha
 *
 * This class serves as the entry point for the Super App, presenting a menu to the user to choose
 * between MyPay and BookMyShow functionalities or to exit the application.
 *
 * The SuperApp class contains:
 * - A main method that initializes the application and shows the menu.
 * - A method to display the main menu and handle user choices.
 *
 * MyPay - A class that manages the payment functionalities, including wallet and bank transactions.
 *
 * The MyPay class contains:
 * - A main method to display MyPay options and handle user inputs.
 * - Methods for checking wallet balance, transferring money, adding money to the wallet,
 *   transferring money from the wallet to the bank account, and checking transfer history.
 * - Helper methods for validating and processing payment details.
 *
 * BookMyShow - A class placeholder for booking movie tickets.
 *
 * - It allows users to select movies and showtimes from various cinemas,
 * - choose seats, and make payments.
 * - The system supports multiple cinemas, movies, and timings. 
 * - Users can select a cinema to see available movies and showtimes or choose a movie to find available cinemas and showtimes.
 * - It also handles seat availability and ticket pricing.
 * 
 * License:
 * This project is licensed under the MIT License.
 * 
 * Note:
 * - The application is designed for simplicity and educational purposes.
 * - The code is structured for clarity and ease of understanding.
 * 
 * Feel free to contribute or provide feedback on this project.
 */

import java.util.*;

/**
 * Main class of the SuperApp application.
 */
class SuperApp {

	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] arg) {
		System.out.println("Hello!");
		System.out.println("Welcome to the Super App.");

		SuperApp.showingMenu();

	}

	/**
	 * psudo Main method to start the application.
	 * Provides a menu for users to choose different functionalities.
	 * 
	 * @param args Command-line arguments (not used).
	 */
	public static void showingMenu() {

		boolean check = true;
		MyPay myPay = new MyPay();
		BookMyShow bookMyShow = new BookMyShow();
		do {
			System.out.println("_________________________________________");
			System.out.println("|1. MyPay.                              |");
			System.out.println("|2. Book my show.                       |");
			System.out.println("|3. To Exit.                            |");
			System.out.println("|_______________________________________|");
			System.out.print("Enter your choice what you want to do : ");
			String choice = scanner.next();
			switch (choice) {
				case "1": {
					System.out.println("You have chosen MyPay");
					myPay.myPayMain();
					break;
				}
				case "2": {
					System.out.println("You have chosen Book my show");
					bookMyShow.bookMyShowMain();
					break;
				}
				case "3": {
					System.out.println("Thank you ! Visit Again.");
					System.exit(0);
					break;
				}
				default: {
					System.out.println("Invalid choice");
					break;
				}
			}
		} while (check);
	}

}// end of class SuperApp

class MyPay {

	static Scanner scanner = new Scanner(System.in);
	static String recipientName;
	static double transferAmount;
	static double BankBalance = 1000000;
	static double WalletBalance = 10000;
	static int numOfTransactions = 0;
	static int Index;
	static String mobileNo;
	static String upiId;
	static String accountNumber;
	static String choice;
	static String[][] transections = new String[50][6];
	static String transectionChoice;

	/**
	 * Main method for the MyPay application.
	 * Displays the main menu and handles user choices.
	 */
	void myPayMain() {

		System.out.println("Welcome to myPay !");
		boolean check = true;
		do {
			System.out.println("_________________________________________");
			System.out.println("|1. Check Wallet Balance.               |");
			System.out.println("|2. Transfer Money.                     |");
			System.out.println("|3. Add money to Wallet.                |");
			System.out.println("|4. Add money Wallet to Bank Account.   |");
			System.out.println("|5. Check Transfer history.             |");
			System.out.println("|6. To Exit.                            |");
			System.out.println("|_______________________________________|");
			System.out.print("MyPay Choice : ");
			choice = scanner.next();
			if (choice.equalsIgnoreCase("back") || choice.equalsIgnoreCase("6")) {
				return;
			}
			MyPay.myPayMenu();// My pay's [1 to 6] funtions exicution method.

		} while (check);
	}

	/**
	 * Handles menu options based on user choice.
	 * 
	 * Executes the functionality related to each menu option.
	 */
	static void myPayMenu() {

		switch (choice) {
			case "1": {
				System.out.println("Your current Wallet Balance is :" + WalletBalance);
				System.out.println("Your current Bank Balance is   :" + BankBalance);
				break;
			}
			case "2": {
				System.out.println("You have chosen Transfer Money.");
				MyPay.transferMoney();// Money Transection.
				break;
			}
			case "3": {
				System.out.println("You have chosen Add money to Wallet.");
				MyPay.internalTasection(3);// Account To Wallet Intarnal Transection
				break;
			}
			case "4": {
				System.out.println("You have chosen Add money form Wallate to Banak Account.");
				MyPay.internalTasection(4);// Wallet To Account Internal Transection
				break;
			}
			case "5": {
				System.out.println("You have chosen to Check Transfer history");
				MyPay.printTransferHistory();// Showing all Money Transfer History
				break;
			}
			default: {
				System.out.println("Enter between 1 to 6 or back ");
			}
		}
	}

	/**
	 * Handles the money transfer process.
	 * 
	 * Allows users to choose the transfer method (Mobile No, UPI, or Account)
	 * and collects recipient details.
	 */
	static void transferMoney() {

		String transferChoice;

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("|1.Transfer to MobileNo     |");
		System.out.println("|2.Transfer to Upi          |");
		System.out.println("|3.Transfer to Account      |");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("Transfer Choice :");
		transferChoice = scanner.next();
		System.out.print("\nEnter Recipient Name:");
		scanner.nextLine();
		recipientName = scanner.nextLine();
		if (transferChoice.equalsIgnoreCase(("1"))) {
			MyPay.getMobileNo();// Getting valid Mobile Number
		} else if (transferChoice.equalsIgnoreCase("2")) {
			MyPay.getUPI();// Getting valid Upi Id
		} else if (transferChoice.equalsIgnoreCase("3")) {
			MyPay.getAccountDetails();// Getting valid Account Details
		} else if (transferChoice.equalsIgnoreCase("back")) {
			return;
		} else {
			System.out.println("Invalid Choice.");
		}
	}

	static void getMobileNo() {

		int count = 0;
		boolean isValidMN = false;
		do {
			System.out.print("Enter Mobile Number :");
			mobileNo = scanner.next();
			if (mobileNo.equalsIgnoreCase("back")) {
				return;
			}
			if (mobileNo.length() == 10) {
				for (int m = 0; m < mobileNo.length(); m++) {
					if (m == 0) {
						if (mobileNo.charAt(m) >= '7' && mobileNo.charAt(m) <= '9') {
							count++;
							continue;
						} else {
							System.out.println("First digit should not be " + mobileNo.charAt(m) +
									"\nIt must be between [7-9].\n And rest between [0-9].");
							count = 0;
							break;
						}
					} else {
						if (mobileNo.charAt(m) >= '0' && mobileNo.charAt(m) <= '9') {
							count++;
							continue;
						} else {
							System.out.println("Enter digits between [0-9]");
							count = 0;
							break;
						}
					}
				}
				if (count == 10) {
					System.out.println("Number Added sucessfully.");
					isValidMN = true;
				}

			} else {
				System.out.println("Enter 10 digits.");
			}
		} while (!isValidMN);
		if (isValidMN) {
			getAmmount("Mobile Number", mobileNo);
		}
	}

	/**
	 * Collects and validates a UPI ID for the transfer.
	 * 
	 * Ensures the UPI ID is in the format of username@bankname and ends with
	 * a valid bank identifier.
	 */
	static void getUPI() {
		boolean isValidUpi = false;
		do {
			System.out.print("Enter UPI ID :");
			upiId = scanner.next();
			int flag = upiId.indexOf("@");
			if (upiId.equalsIgnoreCase("back")) {
				return;
			}
			if (flag != -1) {
				if (upiId.endsWith("oksbi") || upiId.endsWith("ybl") || upiId.endsWith("okaxis")
						|| upiId.endsWith("okhdfcbank")) {
					isValidUpi = true;
				} else {
					System.out.println("invalid bankname\nUPI (typically username@bankname).");
				}
			} else {
				System.out.println("invalid");
			}
		} while (!isValidUpi);
		if (isValidUpi) {
			getAmmount("UPI ID", upiId);

		}

	}

	/**
	 * Collects and validates account details for the transfer.
	 * 
	 * Ensures the account number is valid and handles related operations.
	 */
	static void getAccountDetails() {

		int bankNoCount = 0, accountNoCount = 0, ifscNoCount = 0;
		boolean isValidBank = false, isValidAccount = false, isValidIfsc = false;
		String BankName;
		String accReEnter;
		String IFSC;
		String[][] Bank = {

				{ "SBI", "11" },
				{ "ICICI", "12" },
				{ "HDFC", "14" },
				{ "BOB", "14" },
				{ "AXIS", "15" }

		};
		// bankname
		do {
			System.out.print("Bank Name :--> (SBI/ICICI/HDFC/BOB/AXIS): ");
			BankName = scanner.next();
			if (BankName.equalsIgnoreCase("back")) {
				return;
			}
			for (int m = 0; m < Bank.length; m++) {
				if (BankName.equalsIgnoreCase(Bank[m][0])) {
					bankNoCount = m;
					isValidBank = true;
					break;
				}
			}

		} while (!isValidBank);

		// Account number
		if (isValidBank) {

			do {

				System.out.print("Enter Account number :");
				accountNumber = scanner.next();
				if (accountNumber.equalsIgnoreCase("back")) {
					return;
				}
				// Account number Verify

				int temp = Integer.parseInt(Bank[bankNoCount][1]);

				if (accountNumber.length() == temp) {
					for (int m = 0; m < accountNumber.length(); m++) {
						if (m == 0) {
							if (accountNumber.charAt(m) >= '1' && accountNumber.charAt(m) <= '9') {
								accountNoCount++;
								continue;
							} else {
								System.out.println("Account no does not start with 0");
								accountNoCount = 0;
								break;
							}
						} else {
							if (accountNumber.charAt(m) >= '0' && accountNumber.charAt(m) <= '9') {
								accountNoCount++;
								continue;
							} else {
								System.out.println("Enter digit between [0-9].");
								accountNoCount = 0;
								break;
							}
						}
					}
					if (accountNoCount == accountNumber.length()) {
						System.out.println("Re-enter Account for 2 check verification");
						accReEnter = scanner.next();
						if (accReEnter.equalsIgnoreCase("back")) {
							return;
						}
						if (accReEnter.equals(accountNumber)) {

							isValidAccount = true;
						} else {
							System.out.println("Did'n match!");
							accountNoCount = 0;
						}
					}
				}
			} while (!isValidAccount);

		}

		// IFSC CODE
		/*
		 * Format. The IFSC is an 11-character code
		 * with the first four alphabetic characters representing the bank name,
		 * and the last six characters (usually numeric, but can be alphabetic)
		 * representing the branch.
		 * The fifth character is 0 (zero) and reserved for future use.
		 */

		// IFSC number
		if (isValidAccount) {

			do {
				System.out.print("Enter IFSC code : ");
				IFSC = scanner.next();
				if (IFSC.equalsIgnoreCase("back")) {
					break;
				}
				if (IFSC.length() == 11) {
					for (int m = 0; m < IFSC.length(); m++) {
						if (m >= 0 && m <= 3) {
							if (IFSC.charAt(m) >= 'A' && IFSC.charAt(m) <= 'Z') {
								ifscNoCount++;
								continue;
							}
						} else if (m == 4) {
							if (IFSC.charAt(m) == '0') {
								ifscNoCount++;
								continue;
							}
						} else if (m >= 5 && m <= 10) {
							if (IFSC.charAt(m) >= 'A' && IFSC.charAt(m) <= 'Z'
									|| IFSC.charAt(m) >= '0' && IFSC.charAt(m) <= '9') {
								ifscNoCount++;
								continue;
							}
						}
					}
					if (ifscNoCount == IFSC.length()) {
						System.out.println("IFSC Entered Sucessfully");
						isValidIfsc = true;
					} else {
						System.out.println("Invalid IFSC");
						ifscNoCount = 0;
					}
				}
			} while (!isValidIfsc);

		}
		if (isValidIfsc && isValidAccount && isValidBank) {
			getAmmount("BANK ACCOUNT", accountNumber);

		}
	}

	/**
	 * Collects and validates the transfer amount and initiates the transaction
	 * process.
	 * 
	 * @param paymentMethod The method used for payment (e.g., Mobile Number, UPI
	 *                      ID, Account).
	 * @param paymentId     The identifier related to the payment method.
	 */
	static void getAmmount(String paymentMethod, String paymentId) {

		String choice;
		do {
			System.out.print("Do you want to procede :(Y/N)? : ");
			choice = scanner.next();
			if (choice.equalsIgnoreCase("Y")) {
				System.out.print("Enter the amount to be transferred :");
				transferAmount = scanner.nextInt();
				transectionSelection(transferAmount);
				paymentVerification(transferAmount, paymentMethod, paymentId);
			} else if (choice.equalsIgnoreCase("N")) {
				return;
			}
		} while (!(choice.equalsIgnoreCase("Y")) || (choice.equalsIgnoreCase("N")));
	}

	/**
	 * Selects the source of funds for the transaction based on the available
	 * balance.
	 * 
	 * @param transferAmount The amount to be transferred.
	 */
	static void transectionSelection(double transferAmount) {

		if (transferAmount <= WalletBalance && transferAmount <= BankBalance) {
			do {
				System.out.print("Transection from(Wallet/BankAccount)? : ");
				transectionChoice = scanner.next();
			} while (!(transectionChoice.equalsIgnoreCase("Wallet")
					|| transectionChoice.equalsIgnoreCase("BankAccount")));
		} else if (transferAmount <= WalletBalance && transferAmount >= BankBalance) {
			transectionChoice = "Wallet";
		} else if (transferAmount <= BankBalance && transferAmount > WalletBalance) {
			transectionChoice = "BankAccount";
		} else {
			System.out.println("Insufficient balance!\nPayment not possible.\nManage more funds.");
			return;
		}
	}

	/**
	 * Verifies the payment details and processes the transaction.
	 * 
	 * @param transferAmount The amount to be transferred.
	 * @param paymentMethod  The method used for payment.
	 * @param paymentId      The identifier related to the payment method.
	 */
	static void paymentVerification(double transferAmount, String paymentMethod, String paymentId) {

		if (paymentValidation(transectionChoice)) {
			transectionProcess(transferAmount, transectionChoice);
			if (paymentId.equals("bookMyShowpvt@okaxis")) {
				getHistory(transferAmount, transectionChoice, "Book My show Private limited", paymentMethod, paymentId);
			} else if (paymentId.equals("Last check")) {
				// other payament Methods
			} else {
				getHistory(transferAmount, transectionChoice, recipientName, paymentMethod, paymentId);
			}
			System.out.println("Transection Done Sucessfully ");
		} else {
			System.out.print("Too many atempts sir!\n Try again after 24 hours");
			System.exit(0);
		}
	}

	/**
	 * Processes the transaction by updating the respective balance.
	 * 
	 * @param transferAmount    The amount to be transferred.
	 * @param transactionChoice The source of funds for the transaction.
	 */
	static void transectionProcess(double transferAmount, String transectionChoice) {
		if (transectionChoice.equalsIgnoreCase("Wallet")) {
			WalletBalance -= transferAmount;
		} else if (transectionChoice.equalsIgnoreCase("BankAccount")) {
			BankBalance -= transferAmount;
		}
	}

	/**
	 * Validates the payment PIN for security purposes. give 3 attepts for the
	 * correct pin
	 * 
	 * @param transactionChoice The source of funds for the transaction.
	 * @return true if the PIN is valid; false otherwise.
	 */
	static boolean paymentValidation(String transectionChoice) {
		int n = 1;
		while (n <= 3) {
			String pinCheck, bankPin = "654321", wallatePin = "123456";
			System.out.print("Enter the pin :");
			pinCheck = scanner.next();
			if (transectionChoice.equalsIgnoreCase("Wallet")) {
				if (wallatePin.equals(pinCheck)) {
					return true;
				} else {
					System.out.println("Invalid Pin");
					n++;
				}
			} else if (transectionChoice.equalsIgnoreCase("BankAccount")) {
				if (bankPin.equals(pinCheck)) {
					return true;
				} else {
					System.out.println("Invalid Pin");
					n++;
				}
			}
		}
		return false;
	}

	/**
	 * Handles internal transactions between wallet and bank account.
	 * 
	 * @param type The type of internal transaction (3 for Wallet to Bank, 4 for
	 *             Bank to Wallet).
	 */
	static void internalTasection(int n) {

		if (n == 3) {
			System.out.print("Enter Amount you want to add to your wallate :");
			int bAccToWallet = scanner.nextInt();
			if (bAccToWallet <= BankBalance) {
				WalletBalance += bAccToWallet;
				BankBalance -= bAccToWallet;
				System.out.println("Done sucessfully");
			} else {
				System.out.println("Insufficient balance in your bankAccount.");
			}
		} else if (n == 4) {
			System.out.print("Enter Amount you want to add to your Bank Account from wallate :");
			int walletTobAcc = scanner.nextInt();
			if (walletTobAcc <= WalletBalance) {
				BankBalance += walletTobAcc;
				WalletBalance -= walletTobAcc;
				System.out.println("Done sucessfully");
			} else {
				System.out.println("Insufficient balance in your Wallet.");
			}

		}

	}

	/**
	 * Records the details of a transaction into the transaction history.
	 * 
	 * @param transferAmount    The amount of money transferred.
	 * @param transactionChoice The source of funds used for the transaction (Wallet
	 *                          or BankAccount).
	 * @param recipientName     The name of the recipient of the transfer.
	 * @param paymentMethod     The method used for payment (e.g., Mobile Number,
	 *                          UPI ID, Account).
	 * @param paymentId         The identifier related to the payment method.
	 */
	static void getHistory(double transferAmount, String transectionChoice, String recipientName, String paymentMethod,
			String paymentId) {

		Index = numOfTransactions++;
		transections[Index][0] = "" + numOfTransactions;// Transaction ID
		transections[Index][1] = "" + transferAmount;// Amount Transferred
		transections[Index][2] = transectionChoice;// Source of Funds
		transections[Index][3] = recipientName;// Recipient Name
		transections[Index][4] = paymentMethod;// Payment Method
		transections[Index][5] = paymentId;// Payment ID

	}

	/**
	 * Prints the transaction history for review.
	 */
	static void printTransferHistory() {
		System.out.println("_______________________________________________________________________");
		System.out.println("Transection History :");
		for (int i = 0; i < transections.length; i++) {
			if (i <= Index) {
				System.out.println(transections[i][0] + "). RS " + transections[i][1] + "/- Transfer from "
						+ transections[i][2] +
						" to " + transections[i][3] + "'s " + transections[i][4] + ": " + transections[i][5] + ".");
			}
		}
		System.out.println("_______________________________________________________________________");
	}

}// end of Mypay

class BookMyShow extends MyPay {

	MyPay myPay = new MyPay();
	static Scanner scanner = new Scanner(System.in);
	final static String[] cinemas = { "PVR", "Cinepolis", "Raj Mandir Cinema", "Sunset Drive-In Cinema" };
	final static String[] movies = {
			"Sholay",
			"Prem Rog",
			"Lagaan",
			"3 Idiots",
			"URI",
			"The Nun",
			"Avatar"
	};
	final static String[][][] cin_movi_time = {

			{ // PVR
					{ "Lagaan   ", " 06.00PM TO 09.00PM ", " 07.30PM TO 10.30PM ", " 11.00AM TO 02.00PM " },
					{ "3 Idiots ", " 09.00PM TO 11.30PM ", " 06.30PM TO 09.00PM ", " 01.00AM TO 03.30AM " },
					{ "URI      ", " 08.00PM TO 10.30PM ", " 08.30PM TO 11.00PM ", " 09.00AM TO 11.30AM " }
			},
			{ // Cinepolis
					{ "URI      ", " 08.00PM TO 11.00PM ", " 09.30PM TO 12.30PM ", " 01.00AM TO 03.00PM " },
					{ "Prem Rog ", " 06.00PM TO 09.00PM ", " 07.30PM TO 10.30PM ", " 11.00AM TO 02.00PM " },
					{ "Sholay   ", " 04.00PM TO 06.30PM ", " 05.30PM TO 08.00PM ", " 09.00AM TO 11.30AM " }

			},
			{ // Raj Mandir Cinema
					{ "Avatar   ", " 06.00PM TO 09.00PM ", " 07.30PM TO 10.30PM ", " 08.30PM TO 11.00PM " },
					{ "Sholay   ", " 09.00AM TO 11.30AM ", " 06.00PM TO 09.00PM ", " 05.30PM TO 08.00PM " },
					{ "Lagaan   ", " 06.30PM TO 09.00PM ", " 06.00PM TO 09.00PM ", " 06.30PM TO 09.00PM " }

			},
			{ // Sunset Drive-In Cinema
					{ "The Nun  ", " 04.00PM TO 06.30PM ", " 07.30PM TO 10.30PM ", " 09.00AM TO 11.30AM " },
					{ "Prem Rog ", " 09.00PM TO 11.30PM ", " 09.30PM TO 12.00PM ", " 01.00AM TO 03.00PM " },
					{ "3 Idiots ", " 08.00PM TO 11.00PM ", " 06.00PM TO 09.00PM ", " 06.30PM TO 09.00PM " }
			}

	};

	final static double[][] prices = {

			{ 150.0, 110.0, 90.00 },
			{ 95.0, 170.0, 190.00 },
			{ 50.0, 210.0, 190.00 },
			{ 100.0, 180.0, 190.00 }

	};
	// Initialize seats as available (true = available, false = occupied)
	static boolean[][] seats = {

			{ true, true, true, false, true, true, true },
			{ true, true, true, false, true, true, true },
			{ true, true, true, false, true, true, true },
			{ false, false, false, false, false, false, false },
			{ true, true, true, false, true, true, true },
			{ true, true, true, false, true, true, true },
			{ true, true, true, false, true, true, true }

	};

	static String selectedCinema;
	static String selectedMovie;
	static String selectedTime;
	static int noOfTickets;
	static double totalPrice;

	/**
	 * Main method to start the BookMyShow application, providing options for
	 * booking.
	 */
	void bookMyShowMain() {
		boolean check = false;
		String choice;
		System.out.println("Welcome to the Book My Show.");

		while (!check) {

			System.out.println("_______________________________________________________________________");
			System.out.println("1). Sealect Movie via Cinema");
			System.out.println("2). Sealect Cinema via Movie");
			System.out.println("_______________________________________________________________________");
			System.out.print("Book my show choice : ");

			choice = scanner.next();
			if (choice.equalsIgnoreCase("back")) {
				return;
			} else if (choice.equalsIgnoreCase("1")) {
				BookMyShow.cinemasMenu();
				BookMyShow.cinemaChoice();

			} else if (choice.equalsIgnoreCase("2")) {
				BookMyShow.moviesMenu();
				BookMyShow.movieChoice();
			} else {

			}
		}

	}

	/**
	 * Displays the list of cinemas along with the movies and showtimes available at
	 * each.
	 */
	static void cinemasMenu() {
		System.out.println("____________________________________________________________________________________");
		for (int i = 0; i < cin_movi_time.length; i++) {
			System.out.println((i + 1) + ")." + cinemas[i]);
			for (int j = 0; j < cin_movi_time[i].length; j++) {
				System.out.print((j + 1) + ").");
				for (int k = 0; k < cin_movi_time[i][j].length; k++) {
					if (k == 0) {
						System.out.print(cin_movi_time[i][j][k] + "~~~>");
					} else {
						System.out.print(cin_movi_time[i][j][k]);
					}
				}
				System.out.println(".");
			}
			System.out.println("");
		}
		System.out.println("____________________________________________________________________________________");
	}

	/**
	 * Handles user input to select a cinema and then proceed to movie and time
	 * selection.
	 */
	static void cinemaChoice() {

		System.out.print("Enter Cinema No :(1/2/3/4)? :");
		String cinemaOpt = scanner.next();
		if (cinemaOpt.equalsIgnoreCase("1")) {
			BookMyShow.movieAndTimeChoice(1);
		} else if (cinemaOpt.equalsIgnoreCase("2")) {
			BookMyShow.movieAndTimeChoice(2);
		} else if (cinemaOpt.equalsIgnoreCase("3")) {
			BookMyShow.movieAndTimeChoice(3);
		} else if (cinemaOpt.equalsIgnoreCase("4")) {
			BookMyShow.movieAndTimeChoice(4);
		} else if (cinemaOpt.equalsIgnoreCase("back")) {
			return;
		} else {
			System.out.println("Invalid Input !");

		}

	}

	/**
	 * Allows the user to select a movie and showtime for a given cinema.
	 * 
	 * @param cinemaNumber The selected cinema number.
	 */
	static void movieAndTimeChoice(int cinemaNumber) {

		int movieNumber;
		int timing;
		for (int i = cinemaNumber - 1; i < cinemaNumber; i++) {
			System.out.println((i + 1) + ")." + cinemas[i]);
			for (int j = 0; j < cin_movi_time[i].length; j++) {
				System.out.print((j + 1) + ").");
				for (int k = 0; k < cin_movi_time[i][j].length; k++) {
					if (k == 0) {
						System.out.print(cin_movi_time[i][j][k] + "~~~>");
					} else {
						System.out.print(cin_movi_time[i][j][k]);
					}
				}
				System.out.println(".");
			}
			System.out.println("");
		}
		do {
			System.out.print("Enter Movie number :(1/2/3)? : ");
			movieNumber = scanner.nextInt();

		} while (!(movieNumber < 4 && movieNumber > 0));

		// Timing Printing
		System.out.println(cin_movi_time[cinemaNumber - 1][movieNumber - 1][0] + "\n"
				+ "1)." + cin_movi_time[cinemaNumber - 1][movieNumber - 1][1]
				+ "2)." + cin_movi_time[cinemaNumber - 1][movieNumber - 1][2]
				+ "3)." + cin_movi_time[cinemaNumber - 1][movieNumber - 1][3]);

		do {
			System.out.println("Enter Timings :(1/2/3)? :");
			timing = scanner.nextInt();

		} while (!(timing > 0 && timing < 4));

		// Store the selected cinema, movie, and showtime
		selectedCinema = cinemas[cinemaNumber - 1];
		selectedMovie = cin_movi_time[cinemaNumber - 1][movieNumber - 1][0];
		selectedTime = cin_movi_time[cinemaNumber - 1][movieNumber - 1][timing];
		String line = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
		System.out.println(line + "\n" + selectedCinema + "\n " + selectedMovie + "\n" + selectedTime + ".\n" + line);
		BookMyShow.movieCost(cinemaNumber, movieNumber);
	}

	/**
	 * Calculates and displays the total cost for movie tickets based on the
	 * selected cinema and movie.
	 * Prompts the user to enter the number of tickets and then calculates the total
	 * price.
	 * Displays the seating matrix and invokes the seatPrint method to show
	 * available seats.
	 *
	 * @param cinemaNumber The index of the selected cinema (1-based).
	 * @param movieNumber  The index of the selected movie (1-based).
	 */
	static void movieCost(int cinemaNumber, int movieNumber) {

		double ticketPrice = prices[cinemaNumber - 1][movieNumber - 1];
		System.out.println("Price for one Ticket : Rs" + ticketPrice + "/-.");

		System.out.print("No of Tickets :");
		noOfTickets = scanner.nextInt();

		totalPrice = (ticketPrice * noOfTickets);
		System.out.println("Your total cost is : " + totalPrice);
		// Printing Seating
		BookMyShow.seatPrint(noOfTickets);
	}

	/**
	 * Displays the seating matrix to the user, indicating available and occupied
	 * seats.
	 * Iterates through the seat matrix, printing each seat as either available
	 * ([o]) or occupied ([x]).
	 * Calls the seatSelect method to allow the user to select their seats.
	 *
	 * @param noOfTickets The number of tickets the user wishes to purchase, used
	 *                    for seat selection.
	 */
	static void seatPrint(int noOfTickets) {
		System.out.println("Avaliable seat Matrix");

		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[i].length; j++) {
				System.out.print(seats[i][j] ? "[o]" : "[x]");
			}
			System.out.println();
		}

		System.out.println("Here [o] is Empty seats & [x] is occupied seats");
		// Seat Selection
		BookMyShow.seatSelect(noOfTickets);
	}

	/**
	 * Allows the user to select seats for the number of tickets they wish to
	 * purchase.
	 * Prompts the user to enter the row and column for each seat. Validates the
	 * input to ensure it is within the
	 * seating matrix boundaries and checks if the seat is available. Updates the
	 * seating matrix to mark the
	 * selected seats as occupied. After selecting all seats, displays the updated
	 * seating matrix.
	 *
	 * @param noOfTickets The number of tickets for which the user is selecting
	 *                    seats.
	 */
	static void seatSelect(int noOfTickets) {

		int row, column;
		for (int i = 0; i < noOfTickets; i++) {
			do {
				System.out.print("Enter Seat " + (i + 1) + " Row : ");
				row = scanner.nextInt();
				System.out.print("Enter Seat " + (i + 1) + " column : ");
				column = scanner.nextInt();
			} while (!((row > 0 && row < 8) && (column > 0 && column < 8)));
			if (!(seats[row - 1][column - 1])) {
				System.out.println("Sorry, seat " + row + "x" + column + " is not available." +
						"Please select another seat.");
				i--;
				continue;
			} else {
				seats[row - 1][column - 1] = false;
			}
		}

		// new SeatArrrangement
		System.out.println("New seat matrix :");
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[i].length; j++) {
				System.out.print(seats[i][j] ? "[o]" : "[x]");
			}
			System.out.println();
		}

		BookMyShow.ticketPayment();
	}

	/**
	 * Handles the payment process for the total ticket price.
	 * Invokes the transaction selection method of MyPay to process the payment and
	 * then
	 * verifies the payment
	 * details using a specified UPI ID.
	 */
	static void ticketPayment() {
		transectionSelection(totalPrice);
		paymentVerification(totalPrice, "UPI ID", "bookMyShowpvt@okaxis");

	}

	static void moviesMenu() {
		System.out.println("____________________________________________________________________________________");
		for (int m = 0; m < movies.length; m++) {
			System.out.print(" " + (m + 1) + "]." + movies[m]);
		}
		System.out.print("\n");
		System.out.println("____________________________________________________________________________________");
	}

	static String movieOpt;

	/**
	 * Displays a menu of available movies to the user and take movie choice.
	 */
	static void movieChoice() {
		// movie selection
		System.out.print("Enter movie No [1-7]? :");
		movieOpt = scanner.next();
		if (movieOpt.equalsIgnoreCase("back")) {
			return;
		} else {
			for (int i = 0; i <= 7; i++) {
				String temp = String.valueOf(i);
				if (movieOpt.equalsIgnoreCase(temp)) {
					BookMyShow.cinemaAndTimeChoice(i);
					break;
				} else {
					if (i == 7) {
						System.out.println("invalid choce!");
					}
				}
			}
		}
	}

	/**
	 * Allows the user to select a cinema and movie showtime for a specified movie.
	 * 
	 * @param movieNumber The index of the selected movie (1-based index) from the
	 *                    list of movies.
	 * 
	 *                    The method performs the following steps:
	 *                    1. Displays the selected movie's name.
	 *                    2. Lists available cinemas showing the selected movie.
	 *                    3. Prompts the user to select a cinema from the listed
	 *                    options.
	 *                    4. Displays showtimes available at the selected cinema for
	 *                    the selected movie.
	 *                    5. Prompts the user to select a showtime.
	 *                    6. Prints a summary of the selected cinema, movie, and
	 *                    showtime.
	 *                    7. Calls the `movieCost` method to proceed with the ticket
	 *                    booking process.
	 */
	static void cinemaAndTimeChoice(int movieNumber) {
		int[] tempCinema = { 0, 0, 0, 0 };
		int number = 0;
		int movieIndex = 0;
		int cinemaNumber;
		int timing;
		String selectedMovie = movies[movieNumber - 1];
		System.out.println(selectedMovie);
		for (int cinema = 0; cinema < cin_movi_time.length; cinema++) {
			for (int movie = 0; movie < cin_movi_time[cinema].length; movie++) {
				if (selectedMovie.equals(cin_movi_time[cinema][movie][0].trim())) {
					System.out.println(number + 1 + "]." + cinemas[cinema]);
					tempCinema[number] = cinema + 1;
					number++;
				}
			}
		}

		do {
			System.out.println("Enter Cinema number : ");
			selectedCinema = scanner.next();
			cinemaNumber = Integer.parseInt(selectedCinema);

		} while (!(cinemaNumber <= number && cinemaNumber > 0));

		selectedCinema = (cinemas[tempCinema[cinemaNumber - 1] - 1]);
		System.out.println(selectedCinema);

		int c_i = 0;// c_i --> cinema index
		if (selectedCinema.equals("PVR")) {
			c_i = 0;
		} else if (selectedCinema.equals("Cinepolis")) {
			c_i = 1;
		} else if (selectedCinema.equals("Raj Mandir Cinema")) {
			c_i = 2;
		} else if (selectedCinema.equals("Sunset Drive-In Cinema")) {
			c_i = 3;
		} else {
			System.out.println("nothing come here!");
		}

		// selectedMovie;
		for (int m = 0; m < cin_movi_time[c_i].length; m++) {
			if (selectedMovie.equals(cin_movi_time[c_i][m][0].trim())) {
				System.out.println(cin_movi_time[c_i][m][0].trim());
				movieIndex = m;
			}
		}
		// timing slection
		System.out.print(1 + "]." + cin_movi_time[c_i][movieIndex][1]);
		System.out.print(2 + "]." + cin_movi_time[c_i][movieIndex][2]);
		System.out.print(3 + "]." + cin_movi_time[c_i][movieIndex][3]);
		System.out.println("\n");

		do {
			System.out.println("Enter Timings :(1/2/3)? :");
			timing = scanner.nextInt();
		} while (!(timing > 0 && timing < 4));

		String selectedTime = cin_movi_time[c_i][movieIndex][timing];
		String line = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
		System.out.println(line + "\n" + selectedCinema + "\n " + selectedMovie + "\n" + selectedTime + ".\n" + line);
		BookMyShow.movieCost(c_i + 1, movieIndex + 1);
	}
}// end