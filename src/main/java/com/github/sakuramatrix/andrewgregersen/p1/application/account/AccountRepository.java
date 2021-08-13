package com.github.sakuramatrix.andrewgregersen.p1.application.account;

import ch.qos.logback.classic.Logger;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.update;

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
    session.executeReactive(
        SimpleStatement.builder(
                "INSERT INTO personalFinance.accounts (account_id,first_name,last_name,budget,income) values (?,?,?,?,?)")
            .addPositionalValues(
                account.getUuid(),
                account.getFName(),
                account.getLName(),
                account.getBudget(),
                account.getIncome())
            .build());
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

  public void updateFunds(double funds, UUID uuid) {
    SimpleStatement updateStatement =
        update("account")
            .setColumn("budget", bindMarker())
            .where(Relation.column("account_id").isEqualTo(bindMarker()))
            .build();
    session.executeReactive(
        session.prepare(updateStatement).bind().setUuid(0, uuid).setDouble(3, funds));
  }

  private static final Random ID_GENERATOR = new Random();

  protected static int getNewID() {
    return ID_GENERATOR.nextInt();
  }
}
