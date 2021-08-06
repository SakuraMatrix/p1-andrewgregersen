package com.github.sakuramatrix.andrewgregersen.p1.backend.account;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.util.UUID;

public class Account {
  private String fName;
  private String lName;
  private UUID uuid;

  public static Account from(UUID uuid, String fName, String lName) {
    return new Account(uuid, fName, lName);
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

  public static Account parseResult(ResultSet result) {
    Account account = null;
    for (Row row : result) {
      account =
          from(
              row.get("account_id", UUID.class),
              row.get("first_name", String.class),
              row.get("last_name", String.class));
    }
    return account;
  }
}
