package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/*
 * Capstone skeleton – personal finance tracker.
 * ------------------------------------------------
 * File format  (pipe-delimited)
 *     yyyy-MM-dd|HH:mm:ss|description|vendor|amount
 * A deposit has a positive amount; a payment is stored
 * as a negative amount.
 */
public class FinancialTracker {

    /* ------------------------------------------------------------------
       Shared data and formatters
       ------------------------------------------------------------------ */
    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final String FILE_NAME = "transactions.csv";

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String DATETIME_PATTERN = DATE_PATTERN + " " + TIME_PATTERN;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern(TIME_PATTERN);
    private static final DateTimeFormatter DATETIME_FMT = DateTimeFormatter.ofPattern(DATETIME_PATTERN);

    /* ------------------------------------------------------------------
       Main menu
       ------------------------------------------------------------------ */
    public static void main(String[] args) {
        loadTransactions(FILE_NAME);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Welcome to TransactionApp");
            System.out.println("Choose an option:");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "D" -> addDeposit(scanner);
                case "P" -> addPayment(scanner);
                case "L" -> ledgerMenu(scanner);
                case "X" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
        scanner.close();
    }

    /* ------------------------------------------------------------------
       File I/O
       ------------------------------------------------------------------ */
    /**
     * Load transactions from FILE_NAME.
     * If the file doesn’t exist, create an empty one so that future writes succeed.
     * Each line looks like: date|time|description|vendor|amount
     *
     * @param fileName FILE_NAME is passed down into this parameter
     */
    public static void loadTransactions(String fileName) {
        try {
            File file = new File(fileName); // stores file inside new File object

            // create file if it doesnt exist
            if (!file.exists()){
                file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = br.readLine()) != null) {    // while line isnt null keep reading
                String[] tokens = line.split("\\|"); // split line each time it reads '|'

                if (tokens.length != 5) continue; // if line inside file doesnt have exactly 5 tokens skip it

                LocalDate date = LocalDate.parse(tokens[0], DATE_FMT);
                LocalTime time = LocalTime.parse(tokens[1], TIME_FMT);
                String description = tokens[2];
                String vendor = tokens[3];
                double amount = Double.parseDouble(tokens[4]);

                // creates transaction object using parsed data from file
                Transaction transaction = new Transaction(date, time, description, vendor, amount);

                transactions.add(transaction); // adds new transaction object into the transactions array list
            }
        } catch (IOException e) {
            System.err.println("error");
        }
    }

    /* ------------------------------------------------------------------
       Add new transactions
       ------------------------------------------------------------------ */
    /**
     * Prompt for ONE date+time string in the format
     * "yyyy-MM-dd HH:mm:ss", plus description, vendor, amount.
     * Validate that the amount entered is positive.
     * Store the amount as-is (positive) and append to the file.
     *
     * @param scanner used to read user input
     */
    private static void addDeposit(Scanner scanner) {
        // date + time user input and parse
        try {
            System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss): ");
            String dateTimeInput = scanner.nextLine().trim();

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeInput, DATETIME_FMT);
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            // description
            System.out.print("Enter description: ");
            String description = scanner.nextLine().trim();

            // vendor
            System.out.print("Enter vendor: ");
            String vendor = scanner.nextLine().trim();

            // amount
            System.out.print("Enter amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            // make sure amount is positive
            if (amount <= 0) {
                System.out.println("Deposit amount must be positive.");
                return;
            }
            // creates transaction object using parsed data from file
            Transaction transaction = new Transaction(date, time, description, vendor, amount);

            transactions.add(transaction); // adds new transaction object into the transactions array list

            saveTransaction(transaction); // saves changes to the file

            System.out.println("Deposit added successfully.");
        } catch (Exception e) {
            System.out.println("Invalid input. Deposit not added.");
        }
    }

    /**
     * Prompts user for payment details
     * Amount entered is positive then converts it to negative
     * Creates a transaction and saves it
     *
     * @param scanner used to read user input
     */
    private static void addPayment(Scanner scanner) {
        // TODO
        try {
            System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss): ");
            String dateTimeInput = scanner.nextLine().trim();

            LocalDateTime dateTime = LocalDateTime.parse(dateTimeInput, DATETIME_FMT);
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            // description
            System.out.print("Enter description: ");
            String description = scanner.nextLine().trim();

            // vendor
            System.out.print("Enter vendor: ");
            String vendor = scanner.nextLine().trim();

            // amount
            System.out.print("Enter amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim());

            // make sure amount is positive
            if (amount <= 0) {
                System.out.println("Payment amount must be positive.");
                return;
            }
            //negate the payment entered
            double negatedAmount = amount * -1;
            // creates transaction object using parsed data from file
            Transaction transaction = new Transaction(date, time, description, vendor, negatedAmount);

            transactions.add(transaction); // adds new transaction object into the transactions array list

            saveTransaction(transaction); // saves changes to the file

            System.out.println("Payment added successfully.");
        } catch (Exception e) {
            System.out.println("Invalid input. Payment not added.");
        }
    }

    /**
     * Appends transaction to transaction file.
     *
     * @param transaction takes transaction object and writes it to file
     * */
    private static void saveTransaction(Transaction transaction) {
        try {
            // creates buffered writer and appends all changes to file
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));

            // writes new object into file
            bw.write(transaction.getDate().format(DATE_FMT) +
                    "|" + transaction.getTime().format(TIME_FMT) +
                    "|" + transaction.getDescription() +
                    "|" + transaction.getVendor() +
                    "|" + transaction.getAmount());

            bw.newLine();
            bw.close();

        } catch (IOException e) {
            System.out.println("Error saving transaction");
        }
    }

    /* ------------------------------------------------------------------
       Ledger menu
       ------------------------------------------------------------------ */
    /**
     * Displays the ledger menu
     * Handles user navigation
     *
     * @param scanner Used to read user navigation commands
     * */
    private static void ledgerMenu(Scanner scanner) {

        transactions.sort(Comparator.comparing(Transaction::getDate));

        boolean running = true;
        while (running) {
            System.out.println("Ledger");
            System.out.println("Choose an option:");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String input = scanner.nextLine().trim();

            switch (input.toUpperCase()) {
                case "A" -> displayLedger();
                case "D" -> displayDeposits();
                case "P" -> displayPayments();
                case "R" -> reportsMenu(scanner);
                case "H" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /* ------------------------------------------------------------------
       Display helpers: show data in neat columns
       ------------------------------------------------------------------ */
    /**
     * Display all transactions from the array list
     * */
    private static void displayLedger() {
        printLedgerHeader();
        for (Transaction transaction : transactions) {
            printTransaction(transaction);
        }
    }

    /**
     * Display only the transactions with positive amounts
     * */
    private static void displayDeposits() { /* TODO – only amount > 0               */
        printLedgerHeader();
        for (Transaction deposit : transactions) {
            if (deposit.getAmount() > 0){
                printTransaction(deposit);
            }
        }
    }

    /**
     * Display only the transactions with negative amounts
     * */
    private static void displayPayments() { /* TODO – only amount < 0               */
        printLedgerHeader();
        for (Transaction payment : transactions) {
            if (payment.getAmount() < 0){
                printTransaction(payment);
            }
        }
    }

    /**
     * Prints formatted header for the ledger categories
     * */
    private static void printLedgerHeader () {
        System.out.printf("%-12s %-10s %-20s %-20s %s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-".repeat(75)); // creates line of dashes
    }

    /**
     * Prints 1 transaction in formatted column
     *
     * @param transaction takes the transaction that needs to be printed
     * */
    private static void printTransaction(Transaction transaction) {
            System.out.printf("%-12s %-10s %-20s %-20s %.2f%n", // assigns and holds x amount of spaces starting from the left
                    transaction.getDate().format(DATE_FMT),
                    transaction.getTime().format(TIME_FMT),
                    transaction.getDescription().length() > 20 // CONDDITION if description is longer than 20 characters
                            ? transaction.getDescription().substring(0, 17) + "..." // IF TRUE takes characters from 0 to 16 and adds ... at the end so total characters is still 20
                            : transaction.getDescription(), // if less than 20 characters long just prints it normally
                    transaction.getVendor(),
                    transaction.getAmount());
        }

    /* ------------------------------------------------------------------
       Reports menu
       ------------------------------------------------------------------ */
    /**
     * Displays the reports menu
     * Handles user navigation
     *
     * @param scanner Used to read user navigation commands
     * */
    private static void reportsMenu(Scanner scanner) {
        boolean running = true;
        while (running) {
            System.out.println("Reports");
            System.out.println("Choose an option:");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("6) Custom Search");
            System.out.println("0) Back");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> {monthToDate();}
                case "2" -> {previousMonth(); }
                case "3" -> {yearToDate(); }
                case "4" -> {previousYear(); }
                case "5" -> {searchByVendor(scanner); }
                case "6" -> customSearch(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /**
     * Displays transactions from start of current month up to the current date
     * */
    private static void monthToDate() {
        // get start and end date info
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1); // takes today and creates new date with same year and month but the day is set to 1

        // print header
        System.out.println("MONTH TO DATE");
        printLedgerHeader();

        filterTransactionsByDate(startOfMonth, today); // method takes the start and end date and handles the filtering logic
    }

    /**
     * Displays transactions from the previous month
     * */
    private static void previousMonth() {
        // get start and end date info
        LocalDate today = LocalDate.now();
        LocalDate firstOfLastMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastOfLastMonth = firstOfLastMonth.withDayOfMonth(firstOfLastMonth.lengthOfMonth()); // withDateOfMonth takes the last day of the month

        // print header
        System.out.println("PREVIOUS MONTH");
        printLedgerHeader();

        filterTransactionsByDate(firstOfLastMonth, lastOfLastMonth); // method takes the start and end date and handles the filtering logic
    }

    /**
     * Displays transactions from the start of current year up to the current date
     * */
    private static void yearToDate() {
        // get start and end date info
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.withDayOfYear(1);

        // print header
        System.out.println("YEAR TO DATE");
        printLedgerHeader();

        filterTransactionsByDate(startOfYear, today); // method takes the start and end date and handles the filtering logic
    }

    /**
     * Displays transactions from the previous year
     * */
    private static void previousYear() {
        // get start and end date info
        LocalDate today = LocalDate.now();
        LocalDate firstOfLastYear = today.minusYears(1).withDayOfYear(1);
        LocalDate lastOfLastYear = firstOfLastYear.withDayOfYear(firstOfLastYear.lengthOfYear());

        // print header
        System.out.println("PREVIOUS YEAR");
        printLedgerHeader();

        filterTransactionsByDate(firstOfLastYear, lastOfLastYear); // method takes the start and end date and handles the filtering logic
    }

    /**
     * Prompts user for vendor name
     * Displays matching transactions
     *
     * @param scanner Used to read name of vendor the user wants to search for
     * */
    private static void searchByVendor(Scanner scanner) {
        System.out.print("Search vendor name: ");
        String vendorName = scanner.nextLine().trim(); // saves user input as a string

        // print header
        System.out.println("VENDOR SEARCH");
        printLedgerHeader();

        filterTransactionsByVendor(vendorName); // method takes the string and handles filtering logic
    }
    /* ------------------------------------------------------------------
       Reporting helpers
       ------------------------------------------------------------------ */
    /**
     * Prints transactions between the given dates
     * Tells user if no transactions were found between the given dates
     *
     * @param start the start date
     * @param end the end date
     * */
    private static void filterTransactionsByDate(LocalDate start, LocalDate end) {
        boolean found = false;
        for (Transaction transaction : transactions) {
            if (transaction.getDate().isBefore(start)) continue;
            if (transaction.getDate().isAfter(end)) continue;
            printTransaction(transaction);
            found = true;
        }
        if (!found) System.out.println("No transactions found for that period.");
    }

    /**
     * Prints transactions that match user given vendor
     *
     * @param vendorName User given vendor name that gets compared to list of vendors
     * */
    private static void filterTransactionsByVendor(String vendorName) {
        boolean found = false;
        for (Transaction transaction : transactions) {
            if (!vendorName.equalsIgnoreCase(transaction.getVendor())) continue; // if vendor name does not match any vendor in database it jumps to top and grabs the next transaction
            printTransaction(transaction); // prints transaction if the above condition is false
            found = true; // sets found to true so the (!found) if statement does not print
        }
        if (!found) System.out.println("No transactions found for " + vendorName); // if false will not print
    }

    /**
     * Allows user to filter transactions by date, description, vendor and amount
     * Tells user if no transactions matching the criteria were found
     * */
    private static void customSearch(Scanner scanner) {
        System.out.println("Custom Search. Press 'ENTER' to leave blank");

        LocalDate start = parseDate("Start date (yyyy-MM-dd): ", scanner);

        LocalDate end = parseDate("End date (yyyy-MM-dd): ", scanner);

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Vendor name: ");
        String vendorName = scanner.nextLine().trim();


        Double amount = parseDouble("Amount: ", scanner);

        printLedgerHeader();
        boolean found = false;
        for (Transaction transaction : transactions) {
            if (start != null && transaction.getDate().isBefore(start)) continue;
            if (end != null && transaction.getDate().isAfter(end)) continue;
            if (!description.isBlank() && !transaction.getDescription().equalsIgnoreCase(description)) continue;
            if (!vendorName.isBlank() && !transaction.getVendor().equalsIgnoreCase(vendorName)) continue;
            if (amount != null && transaction.getAmount() != amount) continue;
            printTransaction(transaction);
            found = true;
        }
        if (!found) System.out.println("No transactions found matching your search");
    }

    /* ------------------------------------------------------------------
       Utility parsers (you can reuse in many places)
       ------------------------------------------------------------------ */
    /**
     * Prompts for a date
     *
     * @param prompt The message displayed to the user (asking for date input)
     * @param scanner Used to read the user given date
     * @return parsed LocalDate or null if user leaves input blank
     * */
   private static LocalDate parseDate(String prompt, Scanner scanner) {

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isBlank()) return null;

            try {
                return LocalDate.parse(input, DATE_FMT);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd or press Enter to skip.");
            }
        }
    }

    /**
     * promts for an amount
     *
     * @param prompt The message displayed to the user (asking for amount input)
     * @param scanner Used to read the user given amount
     * @return parsed Double or null if user leaves input blank
     * */
    private static Double parseDouble(String prompt, Scanner scanner) {

       while (true) {
           System.out.print(prompt);
           String input = scanner.nextLine().trim();

           if (input.isBlank()) return null;
           try {
               return Double.parseDouble(input);
           } catch (Exception e) {
               System.out.println("Invalid amount. Please enter a number or press Enter to skip.");
           }
       }
    }
}
