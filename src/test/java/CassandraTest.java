import com.datastax.oss.driver.api.core.CqlSession;
import com.github.sakuramatrix.andrewgregersen.p1.backend.account.Account;
import com.github.sakuramatrix.andrewgregersen.p1.backend.account.AccountRepository;
import com.github.sakuramatrix.andrewgregersen.p1.backend.databaseDriver.DatabaseDriver;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class CassandraTest {

  private CqlSession db;
  private AccountRepository repo;

  @BeforeClass
  public void initAccountInfo() throws TimeoutException {
    db = DatabaseDriver.connect();
    repo = new AccountRepository("account", db);
  }

  @Test
  public void connectionTest() {
    Assert.assertFalse(db.isClosed());
  }

  @Test
  public void insertTest1() {
    UUID id = UUID.randomUUID();
    Account expected = Account.from(id, "test0", "test0");
    repo.create(Account.from(id, "test0", "test0"));
    Account actual = Account.parseResult(repo.read(id));
    Assert.assertEquals(expected, actual);
  }
}
