package com.github.sakuramatrix.andrewgregersen.p1;

import com.github.sakuramatrix.andrewgregersen.p1.application.AppConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringJUnitConfig(classes = AppConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppTest {

  @Autowired ApplicationContext context;

  WebTestClient rest;

  @BeforeAll
  public void setup() {
    this.rest = WebTestClient.bindToApplicationContext(this.context).configureClient().build();
  }

  @Test
  public void postAccount() {
    rest.post()
        .uri("/accounts")
        .body(
            "'{\"id\":1,\"fname\":\"Andrew\",\"lname\":\"Gregersen\",\"income\":1234.50,\"budget\":500.00}'",
            String.class)
        .exchange()
        .expectStatus()
        .is2xxSuccessful();
  }

  @Test
  public void postAccount2() {
    rest.post()
        .uri("/accounts")
        .body(
            "'{\"id\":1,\"fname\":\"Joe\",\"lname\":\"Shmoe\",\"income\":2000.00,\"budget\":1000.00}'",
            String.class)
        .exchange()
        .expectStatus()
        .is2xxSuccessful();
  }

  @Test
  public void getAllAccounts() {
    rest.get().uri("/accounts").exchange().expectStatus().isOk();
  }

  @Test
  public void getSingleAccount() {
    rest.get().uri("/account/1").exchange().expectStatus().isOk();
  }

  @Test
  public void getAllBudgets() {
    rest.get().uri("/budgets").exchange().expectStatus().isOk();
  }

  @Test
  public void getSingleBudget() {
    rest.get().uri("/budget/2").exchange().expectStatus().isOk();
  }

  @Test
  public void updateBudget() {
    rest.get().uri("/budgets/1/1000.54").exchange().expectStatus().isOk();
  }

  @Test
  public void getAllIncomes() {
    rest.get().uri("/incomes").exchange().expectStatus().isOk();
  }

  @Test
  public void getSingleIncome() {
    rest.get().uri("/income/2").exchange().expectStatus().isOk();
  }

  @Test
  public void updateIncome() {
    rest.get().uri("/income/2/5000.00").exchange().expectStatus().isOk();
  }

  @Test
  public void deleteAccount() {
    rest.get().uri("/account/delete/2").exchange().expectStatus().isOk();
  }

  @Test
  public void deleteAccount2() {
    rest.get().uri("/account/delete/1").exchange().expectStatus().isOk();
  }
}
