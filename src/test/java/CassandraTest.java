import com.datastax.oss.driver.api.core.CqlSession;
import com.github.sakuramatrix.andrewgregersen.p1.application.account.AccountRepository;

public class CassandraTest {

  private CqlSession db;
  private AccountRepository repo;

  //  @BeforeClass
  //  public void initAccountInfo() throws TimeoutException {
  //    db = DatabaseDriver.connect();
  //    repo = new AccountRepository("account", db);
  //  }

  //  @Test
  //  public void connectionTest() {
  //    Assert.assertFalse(db.isClosed());
  //  }

  //  @Test
  //  public void insertTest1() {
  //    UUID id = UUID.randomUUID();
  //    Account expected = Account.from(id, "test0", "test0", repo);
  //    repo.create(Account.from(id, "test0", "test0", repo));
  //    Account actual = repo.read(id);
  //    Assert.assertEquals(expected, actual);
  //  }
}
