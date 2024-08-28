import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: ezhelao
 * Date: 19/11/13
 * Time: 12:08
 * To change this template use File | Settings | File Templates.
 */
public class ResponseLoader {

    public JsonNode loadFile(String path) throws IOException {

        URL url =this.getClass().getResource(path);
        url.getFile();
        BufferedReader brd = new BufferedReader(
                new FileReader(url.getFile()));

        String line=null;
        StringBuffer sb = new StringBuffer();

        ObjectMapper om = new ObjectMapper();
        JsonNode  node =om.readValue(brd, JsonNode.class);

        brd.close();

        return node;
    }


    public String getResponePath(String requestPath) throws IOException {
        JsonNode node = loadFile("/data/routes/routes.json");
        Iterator<JsonNode> nodes  = node.path("routes").getElements();

        while(nodes.hasNext())
        {
            JsonNode info =nodes.next();
            String path = info.get("route").getTextValue();
            String file = info.get("file").getTextValue();

            if(path.equalsIgnoreCase(requestPath)){
                return  file;
            }
        }
        return  null;
    }


}
