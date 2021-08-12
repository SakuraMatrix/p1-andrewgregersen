package com.github.sakuramatrix.andrewgregersen.p1.application.account;

import java.util.Objects;
import java.util.UUID;

public class Account {
  private String fName;
  private String lName;
  private UUID uuid;
  private double budget;
  private double income;

  public static Account from(UUID uuid, String fName, String lName) {
    return new Account(uuid, fName, lName);
  }

  public static Account from(String fName, String lName) {
    return new Account(UUID.randomUUID(), fName, lName);
  }

  private Account(UUID uuid, String fName, String lName) {
    this.fName = fName;
    this.lName = lName;
    this.uuid = uuid;
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
