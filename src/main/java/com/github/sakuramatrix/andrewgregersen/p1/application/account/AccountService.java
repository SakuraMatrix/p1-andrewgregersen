package com.github.sakuramatrix.andrewgregersen.p1.application.account;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AccountService {

  private static final Logger log =
      (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("cass.account.server");
  private final AccountRepository repo;

  public static AccountService create(AccountRepository repo) {
    return new AccountService(repo);
  }

  public AccountService(AccountRepository repo) {
    this.repo = repo;
  }

  public Mono<Account> getAccount(String uuid) {
    log.info("Parsing UUID from String");
    return repo.read(Integer.parseInt(uuid));
  }

  public Mono<Integer> deleteAccount(String uuid) {
    log.info("Deleting from DB");
    return repo.deleteAccount(Integer.parseInt(uuid));
  }

  public Flux<Account> getAll() {
    return repo.readAll();
  }

  public Account newAccount(Account account) {
    return repo.create(account);
  }

  public Mono<Double> getBudget(String uuid) {
    log.info("Getting Budget Info from account: " + uuid);
    return repo.readBudget(Integer.parseInt(uuid));
  }

  public Flux<Double> getAllBudgets() {
    log.info("Getting Budget Info from all accounts");
    return repo.readAllBudget();
  }

  public Mono<Double> getIncome(String uuid) {
    log.info("Getting Budget Info from account: " + uuid);
    return repo.readIncome(Integer.parseInt(uuid));
  }

  public Flux<Double> getAllIncomes() {
    log.info("Getting Budget Info from all accounts");
    return repo.readAllIncome();
  }

  public Double updateBudget(Map<String, String> map) {
    log.info("Updating budget");

    return repo.updateBudget(
        Double.parseDouble(map.get("newAmount")), Integer.parseInt(map.get("accountId")));
  }

  public Double updateIncome(String uuid, String newAmount) {
    log.info("Updating Income");
    return repo.updateIncome(Double.parseDouble(newAmount), Integer.parseInt(uuid));
  }
}
