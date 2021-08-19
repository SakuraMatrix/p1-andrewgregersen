package com.github.sakuramatrix.andrewgregersen.p1.application;

import com.datastax.oss.driver.api.core.CqlSession;
import com.github.sakuramatrix.andrewgregersen.p1.App;
import com.github.sakuramatrix.andrewgregersen.p1.application.account.AccountService;
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
                                    .map(App::toByteBuff)
                                    .log("http-server-accounts")))
                    .post(
                        "/accounts",
                        (request, response) ->
                            response.send(
                                request
                                    .receive()
                                    .asString()
                                    .map(App::parseAccount)
                                    .map(accountService::newAccount)
                                    .map(App::toByteBuff)
                                    .log("https-server")))
                    .get(
                        "/account/{accountId}",
                        (request, response) ->
                            response.send(
                                accountService
                                    .getAccount(request.param("accountId"))
                                    .map(App::toByteBuff)
                                    .log("http-server-account")))
                    .get(
                        "/account/delete/{accountId}",
                        (request, response) ->
                            response.send(
                                accountService
                                    .deleteAccount(request.param("accountId"))
                                    .map(App::intToByteBuff)
                                    .log("https-server")))
                    .get(
                        "/budget/{accountId}",
                        (request, response) ->
                            response.send(
                                accountService
                                    .getBudget(request.param("accountId"))
                                    .map(App::doubleToByteBuff)
                                    .log("http-server-budget")))
                    .get(
                        "/budgets",
                        (request, response) ->
                            response.send(
                                accountService
                                    .getAllBudgets()
                                    .map(App::doubleToByteBuff)
                                    .log("http-server-budgets")))
                    .get(
                        "/budgets/{accountId}/{newAmount}",
                        (request, response) ->
                            response.sendString(
                                Mono.just(
                                    accountService.updateBudget(request.params()).toString())))
                    .get(
                        "/income/{accountId}",
                        (request, response) ->
                            response.send(
                                accountService
                                    .getIncome(request.param("accountId"))
                                    .map(App::doubleToByteBuff)
                                    .log("http-server-income")))
                    .get(
                        "/incomes",
                        (request, response) ->
                            response.send(
                                accountService
                                    .getAllIncomes()
                                    .map(App::doubleToByteBuff)
                                    .log("http-server-incomes")))
                    .get(
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
