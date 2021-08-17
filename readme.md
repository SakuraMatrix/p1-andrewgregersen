# Project 1: Personal Finance Tracker

An application that helps the user track and manage their personal finances through a web browswer.

## Features

The features implemented in this project should allow the user to do the following:

1. Access the program through the web browser
    1. Implemented through a Restful API
2. Have the ability to have multiple accounts
    1. And have access to them
    2. Create new accounts
    3. Delete old accounts
    4. Read information about accounts
3. Update the budget of an Account
    1. Increase
    2. Decease
4. Budget their monthly spending
    1. Is able to break it down week by week.
5. Update their information as needed (budget allowance)

## Technologies

The following technologies were used:

1. Junit
2. Java
3. Logback (slfj4)
4. Spring Framework
5. Cassandra DB
6. Maven

## RESTful Endpoints

- GET `/account/{accountId}`: Retrieves the account information from the given ID
    - Example:`{"id":1,"budget":500.0,"income":1000.0,"fname":"Andrew","lname":"Gregersen"}`
- GET `/accounts`: Retrieves account information from all accounts
- POST `/accounts/{JSON String}`: Posts a new account to the DB
- GET `/budget/{accountId}`: Retrieves the budget for a given account
- GET `/budgets`: Retrieves budget information for all accounts
- POST `/budget/{accountId}/{newBudget}`: Updates the budget info for an account
- GET `/income/{accountId}`: Retrieves the income for a given account
- GET `/incomes`: Retrieves income information for all accounts
- POST `/incomes/{accountId}/{newBudget}`: Updates the income information for an account

## Build

Run `mvn clean install` at package directory.

## Run

Run `java -jar nameTBD.jar` in package directory.
