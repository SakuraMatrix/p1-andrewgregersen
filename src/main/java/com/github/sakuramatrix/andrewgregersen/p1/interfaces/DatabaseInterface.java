package com.github.sakuramatrix.andrewgregersen.p1.interfaces;

import com.github.sakuramatrix.andrewgregersen.p1.backend.databaseDriver.DatabaseDriver;

public interface DatabaseInterface {

  static DatabaseDriver connect(String address) {
    return null;
  }

  static DatabaseDriver connect() {
    return null;
  }

  static DatabaseDriver connect(int port) {
    return null;
  }

  void insert(String query);

  void delete(String query);

  void update(String query);

  void read(String query);
}
