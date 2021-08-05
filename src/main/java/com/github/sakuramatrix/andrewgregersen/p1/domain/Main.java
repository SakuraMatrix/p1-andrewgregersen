package com.github.sakuramatrix.andrewgregersen.p1.domain;

import ch.qos.logback.classic.Logger;
import com.github.sakuramatrix.andrewgregersen.p1.backend.DatabaseConnector;
import com.github.sakuramatrix.andrewgregersen.p1.backend.Server;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("main");

  public static void main(String[] args) {
    log.info("Starting application");
    log.info("Connecting to Database...");
    DatabaseConnector db = DatabaseConnector.connect();
    log.info("Starting Server...");
    Server server = Server.start(8080);
  }
}
