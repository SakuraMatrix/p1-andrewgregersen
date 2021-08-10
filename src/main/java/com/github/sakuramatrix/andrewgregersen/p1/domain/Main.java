package com.github.sakuramatrix.andrewgregersen.p1.domain;

import ch.qos.logback.classic.Logger;
import com.github.sakuramatrix.andrewgregersen.p1.application.account.AccountRepository;
import com.github.sakuramatrix.andrewgregersen.p1.application.budget.BudgetRepository;
import com.github.sakuramatrix.andrewgregersen.p1.application.databaseDriver.DatabaseDriver;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.util.UUID;

public class Main {

  private static final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("main");
  private static DatabaseDriver db;
  private static AccountRepository aRepo;
  private static BudgetRepository bRepo;

  public static void main(String[] args) {
    log.info("Starting application");
    log.info("Creating connection to DB");
    db = DatabaseDriver.create();
    log.info("Database successfully created");
    log.info("Testing connection");
    if (db.getSession().isClosed()) {
      log.error("Connection to database is not open! Please check to make sure the DB is running!");
    }
    log.info("Creating Account Repository");
    aRepo = new AccountRepository("personalFinance", db.getSession());
    log.info("Created Account Repository");
    log.info("Creating Budget Repository");
    bRepo = new BudgetRepository();
    log.info("Created Budget Repository");
    log.info("Starting Server...");
    startServer();
    log.info("Waiting for responses...");
  }

  private static void startServer() {
    log.info("Attempting to create a HTTP server");
    HttpServer.create()
        .port(8080)
        .route(
            routes ->
                routes
                    .get(
                        "/test/{param}",
                        (request, response) ->
                            response.sendString(Mono.just("Hello " + request.param("param") + "!")))
                    .get(
                        "/accounts",
                        (request, response) -> response.sendString(Mono.just(aRepo.readAll())))
                    .get(
                        "/account/{accountId}",
                        (request, response) ->
                            response.sendString(
                                Mono.just(
                                    aRepo
                                        .read(UUID.fromString(request.param("accountId")))
                                        .toString())))
                    .get(
                        "/budget/{accountId}",
                        (request, response) ->
                            response.sendString(Mono.just(bRepo.read(request.param("accountId")))))
                    .get(
                        "/budgets",
                        (request, response) -> response.sendString(Mono.just(bRepo.readAll())))
                    .get("budget/{query}", (request, response) -> {})
                    .get("/error", (request, response) -> response.sendNotFound()))
        .bindNow()
        .onDispose()
        .block();
    log.info("Server is now listening for connections!");
  }
}
