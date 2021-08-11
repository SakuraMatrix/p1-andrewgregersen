package com.github.sakuramatrix.andrewgregersen.p1.application.account;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;

import java.util.ArrayList;
import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.update;

public class AccountRepository {

  private static final String TABLE_NAME = "accounts";
  private final String keyspace;
  private final CqlSession session;

  /**
   * Constructor for the Account DAO
   *
   * @param keyspace: The name of the keyspace to connect to
   * @param session: The database connection.
   */
  public AccountRepository(String keyspace, CqlSession session) {
    this.keyspace = keyspace;
    this.session = session;
  }

  public void create(Account account) {
    UUID accountID = UUID.randomUUID();
    account.setUuid(accountID);

    SimpleStatement insertStatement =
        QueryBuilder.insertInto(TABLE_NAME)
            .value("account_id", bindMarker())
            .value("first_name", bindMarker())
            .value("last_name", bindMarker())
            .value("budget", bindMarker())
            .build();

    insertStatement.setKeyspace(keyspace);

    BoundStatement boundStatement =
        session
            .prepare(insertStatement)
            .bind()
            .setUuid(0, account.getUuid())
            .setString(1, account.getFName())
            .setString(2, account.getLName());

    session.execute(boundStatement);
  }

  public Account read(UUID uuid) {

    SimpleStatement readStatement =
        QueryBuilder.selectFrom(keyspace, TABLE_NAME)
            .all()
            .where(Relation.column("account_id").isEqualTo(bindMarker()))
            .build();
    BoundStatement preparedStatement = session.prepare(readStatement).bind().setUuid(0, uuid);
    return parseResult(session.execute(preparedStatement));
  }

  public String readAll() {
    SimpleStatement readStatement = QueryBuilder.selectFrom(keyspace, TABLE_NAME).all().build();
    ArrayList<Account> a = parseManyResult(session.execute(session.prepare(readStatement).bind()));
    StringBuilder builder = new StringBuilder();
    a.forEach(it -> builder.append(it.toString()).append("\n"));
    return builder.toString();
  }

  public void updateFunds(double funds, UUID uuid) {
    SimpleStatement updateStatement =
        update(keyspace, "account")
            .setColumn("budget", bindMarker())
            .where(Relation.column("account_id").isEqualTo(bindMarker()))
            .build();
    session.execute(session.prepare(updateStatement).bind().setUuid(0, uuid).setDouble(3, funds));
  }

  public Account parseResult(ResultSet result) {
    Account account = null;
    for (Row row : result) {
      account =
          Account.from(
              row.get("account_id", UUID.class),
              row.get("first_name", String.class),
              row.get("last_name", String.class),
              this);
    }
    return account;
  }

  public ArrayList<Account> parseManyResult(ResultSet result) {
    ArrayList<Account> accounts = new ArrayList<>();
    for (Row row : result) {
      accounts.add(
          Account.from(
              row.get("account_id", UUID.class),
              row.get("first_name", String.class),
              row.get("last_name", String.class),
              this));
    }
    return accounts;
  }
}
