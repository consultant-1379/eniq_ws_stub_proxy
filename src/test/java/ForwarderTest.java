import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: ezhelao
 * Date: 15/10/13
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class ForwarderTest {
    Forwarder forwarderToTest = new Forwarder();


    @Test
    public void testForwarder()
    {
        Client client  = Client.create();

        WebResource webResource = client.resource("http://159.107.167.56:3000/METADATA/UI");
        String s=webResource.getRequestBuilder().get(String.class);

    }

}
