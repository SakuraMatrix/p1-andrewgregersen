package com.github.sakuramatrix.andrewgregersen.p1.backend.account;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;

import java.util.UUID;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.bindMarker;

public class AccountRepository {

  private static final String TABLE_NAME = "accounts";
  private String keyspace;
  private CqlSession session;

  public AccountRepository(String keyspace, CqlSession session) {
    this.keyspace = keyspace;
    this.session = session;
  }

  private ResultSet executeStatement(SimpleStatement statement) {
    statement.setKeyspace(keyspace);
    return this.session.execute(statement);
  }

  public void create(Account account) {
    UUID accountID = UUID.randomUUID();
    account.setUuid(accountID);

    SimpleStatement insertStatement =
        QueryBuilder.insertInto(TABLE_NAME)
            .value("account_id", bindMarker())
            .value("first_name", bindMarker())
            .value("last_name", bindMarker())
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

  public ResultSet read(UUID uuid) {

    SimpleStatement readStatement =
        QueryBuilder.selectFrom(keyspace, TABLE_NAME)
            .all()
            .where(Relation.column("account_id").isEqualTo(bindMarker()))
            .build();
    BoundStatement preparedStatement = session.prepare(readStatement).bind().setUuid(0, uuid);
    return session.execute(preparedStatement);
  }
}
