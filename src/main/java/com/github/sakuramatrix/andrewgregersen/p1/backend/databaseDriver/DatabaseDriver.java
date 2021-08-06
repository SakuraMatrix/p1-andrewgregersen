package com.github.sakuramatrix.andrewgregersen.p1.backend.databaseDriver;

import ch.qos.logback.classic.Logger;
import com.datastax.oss.driver.api.core.CqlSession;
import org.slf4j.LoggerFactory;

public class DatabaseDriver {

  private static final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("cass");

  private final CqlSession session;

  public static CqlSession connect() {
    return new DatabaseDriver().getSession();
  }

  public DatabaseDriver() {
    log.info("Creating session");
    this.session = CqlSession.builder().build();
    log.info("Successfully created the session");
  }

  public CqlSession getSession() {
    log.info("Retrieving log");
    return session;
  }
}
