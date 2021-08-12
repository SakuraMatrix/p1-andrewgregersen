package com.github.sakuramatrix.andrewgregersen.p1.application.account;

import com.datastax.oss.driver.api.core.CqlSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class AccountServer {

  private final AccountRepository repo;

  public static AccountServer create(CqlSession db, String keyspace) {
    return new AccountServer(db, keyspace);
  }

  public AccountServer(CqlSession db, String keyspace) {
    repo = new AccountRepository(keyspace, db);
  }
  ;

  public Mono<Account> getAccount(UUID uuid) {
    return repo.read(uuid);
  }

  public Flux<Account> getAll() {
    return repo.readAll();
  }

  public void newAccount(String firstName, String lastName) {
    repo.create(Account.from(firstName, lastName));
  }
}
