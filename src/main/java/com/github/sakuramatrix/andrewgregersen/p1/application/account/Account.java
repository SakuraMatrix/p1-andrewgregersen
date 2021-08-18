package com.github.sakuramatrix.andrewgregersen.p1.application.account;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Account {
  private String fName;
  private String lName;
  private int id;
  private double budget;
  private double income;

  public static final Logger log =
      (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("cass.account");

  public static Account from(int id, String fName, String lName) {
    return new Account(id, fName, lName);
  }

  public static Account from(int id, String fName, String lName, double budget, double income) {
    return new Account(id, fName, lName, budget, income);
  }

  public static Account from(String fName, String lName) {
    return new Account(AccountRepository.getNewID(), fName, lName);
  }

  public Account() {}

  private Account(int id, String fName, String lName) {
    log.info("Creating new Account w/out budget/income");
    this.fName = fName;
    this.lName = lName;
    this.id = id;
  }

  public Account(int id, String first_name, String last_name, double budget, double income) {
    log.info("Creating new Account");
    this.fName = first_name;
    this.lName = last_name;
    this.id = id;
    this.budget = budget;
    this.income = income;
  }

  /*
   * Getters and Setters for private fields
   */

  public void setBudget(double budget) {
    this.budget = budget;
  }

  public void setIncome(double income) {
    this.income = income;
  }

  public double getBudget() {
    return budget;
  }

  public double getIncome() {
    return income;
  }

  public String getFName() {
    return fName;
  }

  public void setFName(String fName) {
    this.fName = fName;
  }

  public String getLName() {
    return lName;
  }

  public void setLName(String lName) {
    this.lName = lName;
  }

  public int getId() {
    return id;
  }

  public void setAccountId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Last Name: " + lName + " First Name: " + fName + " Account ID: " + id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Account account = (Account) o;
    return Objects.equals(id, account.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
