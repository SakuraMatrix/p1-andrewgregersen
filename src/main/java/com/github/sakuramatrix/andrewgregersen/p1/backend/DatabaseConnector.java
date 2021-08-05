package com.github.sakuramatrix.andrewgregersen.p1.backend;

import com.github.sakuramatrix.andrewgregersen.p1.interfaces.DatabaseInterface;

public class DatabaseConnector implements DatabaseInterface {

  /**
   * Returns a Database Connection from the given ip address
   *
   * @param address: The ip address at which the Cassandra Server is located on.
   * @return A new Database Connection
   */
  public static DatabaseConnector connect(String address) {
    return new DatabaseConnector(address);
  }

  /**
   * A factory method to return a new Database connection from a given port number.
   *
   * @param port: The port on the localhost where the Cassandra server is located.
   * @return A new Database connection
   */
  public static DatabaseConnector connect(int port) {
    return new DatabaseConnector(port);
  }

  /**
   * A factory method that returns a new Database connection from the localhost on port 8080
   *
   * @return A new Database Connection.
   */
  public static DatabaseConnector connect() {
    return new DatabaseConnector();
  }

  /** Constructor for a basic Cassandra Db connection @ localhost:8080 */
  public DatabaseConnector() {}

  /** Constructor for a basic Cassandra Db connection @ localhost:port */
  public DatabaseConnector(int port) {}

  /** Constructor for a Cassandra Db connection at the given address */
  public DatabaseConnector(String address) {}

  @Override
  public void insert() {}

  @Override
  public void delete() {}

  @Override
  public void update() {}

  @Override
  public void read() {}
}
