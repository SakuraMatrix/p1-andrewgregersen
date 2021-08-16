package com.github.sakuramatrix.andrewgregersen.p1.application.account;

import ch.qos.logback.classic.Logger;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

@Repository
public class AccountRepository {

  private static final Logger log =
      (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("cass.account.repo");
  private static final String TABLE_NAME = "accounts";
  private final String KEYSPACE = "personalFinance";
  private final CqlSession session;

  /**
   * Constructor for the Account DAO
   *
   * @param session: The database connection.
   */
  public AccountRepository(CqlSession session) {
    this.session = session;
  }

  public Account create(Account account) {
    SimpleStatement stmt =
        SimpleStatement.builder(
                "INSERT INTO personalFinance.accounts (account_id,first_name,last_name,budget,income) values (?,?,?,?,?)")
            .addPositionalValues(
                account.getUuid(),
                account.getFName(),
                account.getLName(),
                account.getBudget(),
                account.getIncome())
            .build();
    Flux.from(session.executeReactive(stmt)).subscribe();
    return account;
  }

  public Mono<Account> read(int uuid) {
    log.info("Attempting to read from DB");
    return Mono.from(
            session.executeReactive(
                SimpleStatement.builder("SELECT * FROM personalFinance.accounts WHERE account_id=?")
                    .addPositionalValue(uuid)
                    .build()))
        .map(
            row ->
                Account.from(
                    row.getInt("account_id"),
                    row.getString("first_name"),
                    row.getString("last_name")));
  }

  public Flux<Account> readAll() {
    log.info("Attempting to read from DB");
    return Flux.from(session.executeReactive("SELECT * FROM personalFinance.accounts"))
        .map(
            row ->
                Account.from(
                    row.getInt("account_id"),
                    row.getString("first_name"),
                    row.getString("last_name")));
  }

  public Mono<Double> readIncome(int uuid) {
    return Mono.from(
            session.executeReactive(
                SimpleStatement.builder(
                        "SELECT income from personalFinance.accounts WHERE account_id = ?")
                    .addPositionalValue(uuid)
                    .build()))
        .map(row -> row.getDouble("income"));
  }

  public Flux<Double> readAllIncome() {
    return Flux.from(
            session.executeReactive(
                SimpleStatement.builder("SELECT income from personalFinance.accounts").build()))
        .map(row -> row.getDouble("income"));
  }

  public Double updateIncome(double income, int uuid) {
    SimpleStatement stmt =
        SimpleStatement.builder(
                "UPDATE personalFinance.accounts SET income = ? WHERE account_id = ? IF EXISTS")
            .addPositionalValues(income, uuid)
            .build();
    session.executeReactive(stmt);
    return income;
  }

  public Mono<Double> readBudget(int uuid) {
    return Mono.from(
            session.executeReactive(
                SimpleStatement.builder(
                        "SELECT budget from personalFinance.accounts WHERE account_id = ?")
                    .addPositionalValue(uuid)
                    .build()))
        .map(row -> row.getDouble("budget"));
  }

  public Flux<Double> readAllBudget() {
    return Flux.from(
            session.executeReactive(
                SimpleStatement.builder("SELECT budget from personalFinance.accounts").build()))
        .map(row -> row.getDouble("budget"));
  }

  public Double updateBudget(double budget, int uuid) {
    SimpleStatement stmt =
        SimpleStatement.builder(
                "UPDATE personalFinance.accounts SET budget = ? WHERE account_id = ? IF EXISTS")
            .addPositionalValues(budget, uuid)
            .build();
    session.executeReactive(stmt);
    return budget;
  }

  private static final Random ID_GENERATOR = new Random();

  protected static int getNewID() {
    return ID_GENERATOR.nextInt();
  }
}
