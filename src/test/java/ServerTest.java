import com.github.sakuramatrix.andrewgregersen.p1.backend.Server;
import org.junit.Assert;
import org.junit.Test;

public class ServerTest {
  private Server server;

  @Test
  public void setServer() {
    server = Server.start(9485);
    Assert.assertNotNull(server);
  }

  @Test
  public void connectionTest1() {}
}
