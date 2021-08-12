package com.github.sakuramatrix.andrewgregersen.p1.domain;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sakuramatrix.andrewgregersen.p1.application.account.Account;
import com.github.sakuramatrix.andrewgregersen.p1.application.account.AccountServer;
import com.github.sakuramatrix.andrewgregersen.p1.application.databaseDriver.DatabaseDriver;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

import java.util.UUID;

public class Main {

  private static final Logger log = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("main");
  private static DatabaseDriver db;
  private static AccountServer aServer;

  public static void main(String[] args) {
    log.info("Starting application");
    log.info("Creating connection to DB");
    db = DatabaseDriver.create();
    log.info("Database successfully created");
    log.info("Testing connection");
    if (db.getSession().isClosed()) {
      log.error("Connection to database is not open! Please check to make sure the DB is running!");
    }
    log.info("Creating Account Server");
    aServer = AccountServer.create(db.getSession(), "personalFinance");
    log.info("Created Account Repository");
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
                        (request, response) ->
                            response.send(aServer.getAll().map(Main::toByteBuff)))
                    .get(
                        "/account/{accountId}",
                        (request, response) ->
                            response.send(
                                aServer
                                    .getAccount(UUID.fromString(request.param("accountId")))
                                    .map(Main::toByteBuff)))
                    .get(
                        "/error",
                        (request, response) ->
                            response.status(404).addHeader("Message", "What have you done?!?!?!?")))
        .bindNow()
        .onDispose()
        .block();
    log.info("Server is now listening for connections!");
  }

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private static ByteBuf toByteBuff(Object o) {
    try {
      return Unpooled.buffer()
          .writeBytes(OBJECT_MAPPER.writerFor(Account.class).writeValueAsBytes(o));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
