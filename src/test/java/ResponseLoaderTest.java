import org.junit.Test;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ezhelao
 * Date: 19/11/13
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public class ResponseLoaderTest {


    @Test
    public void testLoadJSONFileFromRepo() throws IOException {
        ResponseLoader responseLoader = new ResponseLoader();

        String path=responseLoader.getResponePath("/SUBSCRIBER/EVENT_ANALYSIS");
    }

    @Test
    public void testLoadJSONFile() throws IOException
    {
        ResponseLoader responseLoader = new ResponseLoader();
        String s=responseLoader.loadFile("/response/ui/UIMetaData.json").toString();


        String path = responseLoader.getResponePath("/METADATA/UI");
        String s2=responseLoader.loadFile(responseLoader.getResponePath("/METADATA/UI")).toString();
    }


    @Test
    public void testLoadJSONFileResponse() throws IOException {
        ResponseLoader responseLoader = new ResponseLoader();

         String content =responseLoader.loadFile(responseLoader.getResponePath("/SUBSCRIBER/EVENT_ANALYSIS")).getTextValue();

    }




}
