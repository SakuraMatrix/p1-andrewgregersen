package com.github.sakuramatrix.andrewgregersen.p1.application.account;

import java.util.Objects;

public class Account {
  private String fName;
  private String lName;
  private int uuid;
  private double budget;
  private double income;

  public static Account from(int uuid, String fName, String lName) {
    return new Account(uuid, fName, lName);
  }

  public static Account from(String fName, String lName) {
    return new Account(AccountRepository.getNewID(), fName, lName);
  }

  private Account(int uuid, String fName, String lName) {
    this.fName = fName;
    this.lName = lName;
    this.uuid = uuid;
  }

  private Account(int uuid, String fName, String lName, double budget, double income) {
    this.fName = fName;
    this.lName = lName;
    this.uuid = uuid;
    this.budget = budget;
    this.income = income;
  }

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

  public int getUuid() {
    return uuid;
  }

  public void setUuid(int uuid) {
    this.uuid = uuid;
  }

  @Override
  public String toString() {
    return "Last Name: " + lName + " First Name: " + fName + " Account ID: " + uuid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Account account = (Account) o;
    return Objects.equals(uuid, account.uuid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}
