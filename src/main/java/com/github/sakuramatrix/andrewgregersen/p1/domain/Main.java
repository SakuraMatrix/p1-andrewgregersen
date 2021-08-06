package com.github.sakuramatrix.andrewgregersen.p1.domain;

import ch.qos.logback.classic.Logger;
import com.datastax.oss.driver.api.core.CqlSession;
import com.github.sakuramatrix.andrewgregersen.p1.backend.Server;
import com.github.sakuramatrix.andrewgregersen.p1.backend.databaseDriver.DatabaseDriver;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("main");

  public static void main(String[] args) {
    log.info("Starting application");
    log.info("Connecting to Database...");
    CqlSession db = DatabaseDriver.connect();
    log.info("Starting Server...");
    Server server = Server.start(8080);
  }
}
