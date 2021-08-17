package com.github.sakuramatrix.andrewgregersen.p1.application;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.sakuramatrix.andrewgregersen.p1.application.account.AccountService;
import com.github.sakuramatrix.andrewgregersen.p1.domain.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

@Configuration
@ComponentScan
public class AppConfig {
  @Autowired AccountService accountService;

  @Bean
  public CqlSession session() {
    return CqlSession.builder().build();
  }

  @Bean
  public DisposableServer disposableServer() {
    return HttpServer.create()
        .port(8080)
        .route(
            routes ->
                routes
                    .get(
                        "/test/{param}",
                        (request, response) ->
                            response.sendString(
                                Mono.just("Hello " + request.param("param") + "!")
                                    .log("http-server-test")))
                    .get(
                        "/accounts",
                        (request, response) ->
                            response.send(
                                accountService
                                    .getAll()
                                    .map(Main::toByteBuff)
                                    .log("http-server-accounts")))
                    .post(
                        "/accounts",
                        (request, response) ->
                            response.send(
                                request
                                    .receive()
                                    .asString()
                                    .map(Main::parseAccount)
                                    .map(accountService::newAccount)
                                    .map(Main::toByteBuff)
                                    .log("https-server")))
                    .get(
                        "/account/{accountId}",
                        (request, response) ->
                            response.send(
                                accountService
                                    .getAccount(request.param("accountId"))
                                    .map(Main::toByteBuff)
                                    .log("http-server-account")))
                    .get(
                        "/budget/{accountId}",
                        (request, response) ->
                            response.send(
                                accountService
                                    .getBudget(request.param("accountId"))
                                    .map(Main::doubleToByteBuff)
                                    .log("http-server-budget")))
                    .get(
                        "/budgets",
                        (request, response) ->
                            response.send(
                                accountService
                                    .getAllBudgets()
                                    .map(Main::doubleToByteBuff)
                                    .log("http-server-budgets")))
                    .post(
                        "/budgets/{accountId}/{newAmount}",
                        (request, response) ->
                            response.sendObject(
                                accountService.updateBudget(
                                    request.param("account_id"), request.param("newAmount"))))
                    .get(
                        "/income/{accountId}",
                        (request, response) ->
                            response.send(
                                accountService
                                    .getIncome(request.param("accountId"))
                                    .map(Main::doubleToByteBuff)
                                    .log("http-server-income")))
                    .get(
                        "/incomes",
                        (request, response) ->
                            response.send(
                                accountService
                                    .getAllIncomes()
                                    .map(Main::doubleToByteBuff)
                                    .log("http-server-incomes")))
                    .post(
                        "/income/{accountId}/{newAmount}",
                        (request, response) ->
                            response.sendObject(
                                accountService.updateIncome(
                                    request.param("account_id"), request.param("newAmount"))))
                    .get(
                        "/error",
                        (request, response) ->
                            response.status(404).addHeader("Message", "What have you done?!?!?!?")))
        .bindNow();
  }
}
