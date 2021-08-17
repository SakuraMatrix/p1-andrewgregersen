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
  // logger for the class
  private static final Logger log =
      (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("cass.account.repo");
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
                account.getId(),
                account.getFName(),
                account.getLName(),
                account.getBudget(),
                account.getIncome())
            .build();
    Flux.from(session.executeReactive(stmt)).subscribe();
    return account;
  }

  /**
   * Reads from the Database a single account
   *
   * @param uuid: The users account number to read
   * @return: A Mono for an Account object in a form usable to the HTTP server
   */
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
                    row.getString("last_name"),
                    row.getDouble("income"),
                    row.getDouble("budget")));
  }

  /**
   * Reads all accounts from the DB
   *
   * @return: A Flux of all accounts in the database
   */
  public Flux<Account> readAll() {
    log.info("Attempting to read from DB");
    return Flux.from(session.executeReactive("SELECT * FROM personalFinance.accounts"))
        .map(
            row ->
                Account.from(
                    row.getInt("account_id"),
                    row.getString("first_name"),
                    row.getString("last_name"),
                    row.getDouble("income"),
                    row.getDouble("budget")));
  }

  /**
   * Reads the income from a single account
   *
   * @param uuid: The account to read from
   * @return A Double wrapped in a Mono for ease of use in the HTTP-Server
   */
  public Mono<Double> readIncome(int uuid) {
    return Mono.from(
            session.executeReactive(
                SimpleStatement.builder(
                        "SELECT income from personalFinance.accounts WHERE account_id = ?")
                    .addPositionalValue(uuid)
                    .build()))
        .map(row -> row.getDouble("income"));
  }

  /**
   * Reads all incomes from all accounts in the Database
   *
   * @return A Double wrapped in a Flux for ease of use in the HTTP-Server
   */
  public Flux<Double> readAllIncome() {
    return Flux.from(
            session.executeReactive(
                SimpleStatement.builder("SELECT income from personalFinance.accounts").build()))
        .map(row -> row.getDouble("income"));
  }

  /**
   * Updates the income that an account has associated with it
   *
   * @param income: The new income to be written to the DB
   * @param uuid: The account to be updated
   * @return The new amount that is stored in the DB
   */
  public Double updateIncome(double income, int uuid) {
    SimpleStatement stmt =
        SimpleStatement.builder(
                "UPDATE personalFinance.accounts SET income = ? WHERE account_id = ? IF EXISTS")
            .addPositionalValues(income, uuid)
            .build();
    session.executeReactive(stmt);
    return income;
  }

  /**
   * Reads the budget for a single account from the database
   *
   * @param uuid: The account to read from
   * @return A Double wrapped in a Mono for ease of use in the HTTP-Server
   */
  public Mono<Double> readBudget(int uuid) {
    return Mono.from(
            session.executeReactive(
                SimpleStatement.builder(
                        "SELECT budget from personalFinance.accounts WHERE account_id = ?")
                    .addPositionalValue(uuid)
                    .build()))
        .map(row -> row.getDouble("budget"));
  }

  /**
   * Reads the budgets for all accounts in the database
   *
   * @return A Double wrapped in a Flux for ease of use in the HTTP-Server
   */
  public Flux<Double> readAllBudget() {
    return Flux.from(
            session.executeReactive(
                SimpleStatement.builder("SELECT budget from personalFinance.accounts").build()))
        .map(row -> row.getDouble("budget"));
  }

  /**
   * Updates the budget that is related to a given account
   *
   * @param budget: The new budget value
   * @param uuid: The account to be updated
   * @return The new amount stored in the DB
   */
  public Double updateBudget(double budget, int uuid) {
    SimpleStatement stmt =
        SimpleStatement.builder(
                "UPDATE personalFinance.accounts SET budget = ? WHERE account_id = ? IF EXISTS")
            .addPositionalValues(budget, uuid)
            .build();
    session.executeReactive(stmt);
    return budget;
  }

  /** Used for creating new accounts that dont have a given user Id */
  private static final Random ID_GENERATOR = new Random();

  protected static int getNewID() {
    return ID_GENERATOR.nextInt();
  }
}
