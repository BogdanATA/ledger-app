package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
     * • If the file doesn’t exist, create an empty one so that future writes succeed.
     * • Each line looks like: date|time|description|vendor|amount
     */
    public static void loadTransactions(String fileName) {
        // TODO: create file if it does not exist, then read each line,
        //       parse the five fields, build a Transaction object,
        //       and add it to the transactions list.
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
     */
    private static void addDeposit(Scanner scanner) {
        // TODO
        // date + time user input and parse
        try {
            System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss: ");
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
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Deposit not added.");
        }
    }
    // writes any new objects into file and saves it
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

    /**
     * Same prompts as addDeposit.
     * Amount must be entered as a positive number,
     * then converted to a negative amount before storing.
     */
    private static void addPayment(Scanner scanner) {
        // TODO
        try {
            System.out.print("Enter date and time (yyyy-MM-dd HH:mm:ss: ");
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
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Payment not added.");
        }
    }

    /* ------------------------------------------------------------------
       Ledger menu
       ------------------------------------------------------------------ */
    private static void ledgerMenu(Scanner scanner) {
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
    private static void displayLedger() { /* TODO – print all transactions in column format */
        printLedgerHeader();
        printTransaction("all");
    }

    private static void displayDeposits() { /* TODO – only amount > 0               */
        printLedgerHeader();
        printTransaction("deposits");
    }

    private static void displayPayments() { /* TODO – only amount < 0               */
        printLedgerHeader();
        printTransaction("payments");
    }

    private static void printLedgerHeader () {
        System.out.printf("%-12s %-10s %-20s %-20s %s%n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-".repeat(75)); // creates line of dashes
    }

    private static void printTransaction(String filter) {
        for (Transaction transaction : transactions) { // Transaction = type | transaction = new variable created | transactions = array list
            /*if the filter matches AND the amount condition is true -> skip (continue)
            if the filter does not match → ignore this line entirely
            if the filter matches AND the amount condition is false -> fall through to print*/
            if (filter.equals("deposits") && transaction.getAmount() < 0) continue;
            if (filter.equals("payments") && transaction.getAmount() > 0) continue;


            System.out.printf("%-12s %-10s %-20s %-20s %.2f%n", // assigns and holds x amount of spaces from the left
                    transaction.getDate().format(DATE_FMT),
                    transaction.getTime().format(TIME_FMT),
                    transaction.getDescription().length() > 20 // CONDDITION if description is longer than 20 characters
                            ? transaction.getDescription().substring(0, 17) + "..." // IF TRUE takes characters from 0 to 16 and adds ... at the end so total characters is still 20
                            : transaction.getDescription(), // if less than 20 characters long just prints it normally
                    transaction.getVendor(),
                    transaction.getAmount());
        }
    }

    /* ------------------------------------------------------------------
       Reports menu
       ------------------------------------------------------------------ */
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
                case "1" -> {/* TODO – month-to-date report */ }
                case "2" -> {/* TODO – previous month report */ }
                case "3" -> {/* TODO – year-to-date report   */ }
                case "4" -> {/* TODO – previous year report  */ }
                case "5" -> {/* TODO – prompt for vendor then report */ }
                case "6" -> customSearch(scanner);
                case "0" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    /* ------------------------------------------------------------------
       Reporting helpers
       ------------------------------------------------------------------ */
    private static void filterTransactionsByDate(LocalDate start, LocalDate end) {
        // TODO – iterate transactions, print those within the range
    }

    private static void filterTransactionsByVendor(String vendor) {
        // TODO – iterate transactions, print those with matching vendor
    }

    private static void customSearch(Scanner scanner) {
        // TODO – prompt for any combination of date range, description,
        //        vendor, and exact amount, then display matches
    }

    /* ------------------------------------------------------------------
       Utility parsers (you can reuse in many places)
       ------------------------------------------------------------------ */
    private static LocalDate parseDate(String s) {
        /* TODO – return LocalDate or null */
        return null;
    }

    private static Double parseDouble(String s) {
        /* TODO – return Double   or null */
        return null;
    }
}
