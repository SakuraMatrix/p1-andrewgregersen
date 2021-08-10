package com.github.sakuramatrix.andrewgregersen.p1.application.account;

import java.util.Objects;
import java.util.UUID;

public class Account {
  private String fName;
  private String lName;
  private UUID uuid;
  private double budget;
  private double income;

  private final AccountRepository repo;

  public static Account from(UUID uuid, String fName, String lName, AccountRepository repo) {
    return new Account(uuid, fName, lName, repo);
  }

  private Account(UUID uuid, String fName, String lName, AccountRepository repo) {
    this.fName = fName;
    this.lName = lName;
    this.uuid = uuid;
    this.repo = repo;
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

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public double getFunds() {
    return this.budget;
  }

  public double addFunds(double moreFunds) {
    this.budget -= moreFunds;
    repo.updateFunds(budget, uuid);
    return budget;
  }

  public double removeFunds(double lessFunds) {
    this.budget -= lessFunds;
    repo.updateFunds(budget, uuid);
    return budget;
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
