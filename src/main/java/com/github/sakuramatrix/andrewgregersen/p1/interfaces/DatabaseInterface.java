package com.github.sakuramatrix.andrewgregersen.p1.interfaces;

import com.github.sakuramatrix.andrewgregersen.p1.backend.DatabaseConnector;

public interface DatabaseInterface {

  static DatabaseConnector connect(String address) {
    return null;
  }

  static DatabaseConnector connect() {
    return null;
  }

  static DatabaseConnector connect(int port) {
    return null;
  }

  void insert();

  void delete();

  void update();

  void read();
}
