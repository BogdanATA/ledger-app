# Project Title

## Description of the Project

Briefly describe what this Java console application does. Mention its purpose and who the intended users are. Explain the main functionality and what problems it aims to solve.
This application is a personal finance tracker that allows users to record and track their financial transactions. This application is intended for users who want 
to keep track of their finances and be able to find specific transactions that they are looking for. Users can add deposits and payments which get added into a file
that saves all the data. The application provides a way for users filter their transactions by date, description, vendor and amount. The application helps user get 
better visibility of their spending and income.

## User Stories

List the user stories that guided the development of your application. Format these stories as: "As a [type of user], I want [some goal] so that [some reason]."

- As a user, I want to be able to input my data, so that the application can process it accordingly.
- As a user, I want to receive immediate feedback, so I can understand what to do next.
- As a user, I want to see a home screen menu, so that I can choose what I want to do in the app.
- As a user, I want to add a deposit, so that I can record money coming into my account.
- As a user, I want to make a payment, so that I can record money leaving my account.
- As a user, I want my transactions saved to a file, so that my data is not lost when the app closes.
- As a user, I want the app to read my transactions from a file, so that I can view previous activity.
- As a user, I want to open the ledger screen, so that I can view and filter my transactions.
- As a user, I want to view all ledger entries, so that I can see my full transaction history.
- As a user, I want to view only deposits, so that I can see all money added to my account.
- As a user, I want to view only payments, so that I can see all money spent from my account.
- As a user, I want to open a reports screen, so that I can run pre-defined transaction reports.
- As a user, I want to view month-to-date transactions, so that I can see activity from the current month.
- As a user, I want to view previous month transactions, so that I can review last month’s account activity.
- As a user, I want to view year-to-date transactions, so that I can review activity from the current year.
- As a user, I want to view previous year transactions, so that I can review last year’s financial activity.
- As a user, I want to search transactions by vendor, so that I can find all transactions related to a specific person or company.
- As a user, I want to have a custom search option so I can find transactions fufilling multiple criteria

## Setup

Instructions on how to set up and run the project using IntelliJ IDEA.

### Prerequisites

- IntelliJ IDEA: Ensure you have IntelliJ IDEA installed, which you can download from [here](https://www.jetbrains.com/idea/download/).
- Java SDK: Make sure Java SDK is installed and configured in IntelliJ.

### Running the Application in IntelliJ

Follow these steps to get your application running within IntelliJ IDEA:

1. Open IntelliJ IDEA.
2. Select "Open" and navigate to the directory where you cloned or downloaded the project.
3. After the project opens, wait for IntelliJ to index the files and set up the project.
4. Find the main class with the `public static void main(String[] args)` method.
5. Right-click on the file and select 'Run 'FinancialTracker.main()'' to start the application.

## Technologies Used

- Java: JDK 17

## Demo

![Application Screenshot](ledger-app-showcase.gif)

## Future Work

- Make it so when user enters a description it could be partial but still find a match
- Add a way for user to update an existing transaction if they entered it wrong 
- Add option to input current time and date to avoid manually typing it in
- Add option for user to only input year when filtering
- Do not let user input more than 35 characters into description

## Resources

- [Java Visual Learning Hub](https://raymaroun.github.io/yearup-java-visuals/index.html)
- [RayMaroun solution Repos](https://github.com/RayMaroun/yearup-spring-section-8-2026)
- [w3schools](https://www.w3schools.com/java/)
- [Java Time Package](https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html)

## Team Members

- **Bogdan Atamyeyev** - Lead developer.

## Thanks

- Thank you to [Raymond Maroun] for continuous support and guidance.
- A special thanks to all teammates for their dedication and teamwork.
 