import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: ezhelao
 * Date: 14/10/13
 * Time: 13:55
 * To change this template use File | Settings | File Templates.
 */


@Path("/")
@Stateless
public class Forwarder {



    static final String STUB_HOST="STUB_HOST";

    static final String ENIQ_PROPERTIES_NAME="Eniq_Event_Properties";
    static final String APPLICATION_NAME="APPLICATION_NAME";
    Logger logger = Logger.getLogger(Forwarder.class.toString());
    ResponseLoader responseLoader = new ResponseLoader();





    String host =null;
    String applicationName = "";

    @Context
    UriInfo uriInfo;

    private void getJNDIProperties () throws NamingException {
        InitialContext ctx = new InitialContext();
        Properties properties= (Properties)ctx.lookup(ENIQ_PROPERTIES_NAME);
        host = properties.getProperty(STUB_HOST);
        applicationName = properties.getProperty(APPLICATION_NAME);
        if(applicationName==null)
        {
            applicationName="";
        }
    }



    /*
    @GET
    @Path("{subResources: [a-zA-Z0-9_/]*}")
    public String getMessage(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders) throws IOException {

        String content="{}";
        try {
            String baseUri = uriInfo.getBaseUri().toASCIIString();
            String fullUri = uriInfo.getPath();
            logger.log(Level.INFO, "path is " + baseUri+"+++"+fullUri);

            fullUri = "/"+fullUri.replace(baseUri, "");
            logger.log(Level.INFO, "path is " + fullUri);

            content = responseLoader.loadFile(responseLoader.getResponePath(fullUri)).toString();
        } catch (Exception ex) {

        }

        logger.log(Level.FINEST, "returning  " + content);

        return content;

    }
    */

    @POST
    @Path("{subResources: [a-zA-Z0-9_/]*}")
    public String postMessage(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders,@FormParam("username") String username,
                              @FormParam("version")String version,@FormParam("settings")String settings) throws IOException {

        String content="{}";
        try {
            String baseUri = uriInfo.getBaseUri().toASCIIString();
            String fullUri = uriInfo.getPath();
            logger.log(Level.INFO, "path is " + baseUri+"+++"+fullUri);

            fullUri = "/"+fullUri.replace(baseUri, "");
            logger.log(Level.INFO, "path is " + fullUri);

            content = responseLoader.loadFile(responseLoader.getResponePath(fullUri)).toString();
        } catch (Exception ex) {

        }

        logger.log(Level.FINEST, "returning  " + content);

        return content;

    }


    @PUT
    @Consumes(MediaType.WILDCARD)

    @Path("{subResources: [a-zA-Z0-9_/]*}")
    public String putMessage(@Context UriInfo uriInfo,@FormParam("username") String username,
                              MultivaluedMap<String,String> formData) throws IOException {

        try
        {
            getJNDIProperties ();
        }
        catch (Exception ex)
        {
            logger.log(Level.SEVERE,"fail to get JNDI exception");

        }

        if(host==null)
        {
            return "host not set, please set host name STUB_HOST under Eniq_Event_Properties  ";
        }

        Client client  = Client.create();
        String baseUri = uriInfo.getBaseUri().toASCIIString();
        String fullUri = uriInfo.getRequestUri().toASCIIString();
        fullUri =fullUri.replace(baseUri,"");
        String path="";

        if("".equals(applicationName))
        {
            if(host.charAt(host.length()-1)!='/')
            {
                path =    host+"/"+fullUri;
            }
            else
            {
                 path =    host+fullUri;
            }
        }
        else
        {
            if(host.charAt(host.length()-1)!='/')
            {
                path =host+"/"+applicationName+"/"+fullUri;
            }
            else
            {
                path = host +  applicationName+"/"+fullUri;
            }
        }

        path=path.replace(" ","");
        /*
        MultivaluedMap formData = new MultivaluedMapImpl();
        formData.add("username", username);
        formData.add("version", version);
        formData.add("settings",settings);*/


        StringBuffer sb = new StringBuffer();

        for (String val : formData.keySet())
        {
            sb.append(val+":");
            for (String valx : formData.get(val))
            {
                sb.append(""+valx.substring(0,Math.min(valx.length(),50)));
            }
            sb.append("\n");
        }
        logger.log(Level.SEVERE,"Sending request(PUT) to "+path +" form data:"+sb.toString());

        WebResource webResource = client.resource(path);
        String s=webResource.getRequestBuilder().accept(MediaType.APPLICATION_JSON)
                .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).put(String.class,formData);
        logger.log(Level.SEVERE,"Result: "+s.substring(0,Math.min(s.length(),50))+"...");

        return s;

    }

    @DELETE
    @Path("{subResources: [a-zA-Z0-9_/]*}")
    public String deleteMessage(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders) throws IOException {

        String content="{}";
        try {
            String baseUri = uriInfo.getBaseUri().toASCIIString();
            String fullUri = uriInfo.getPath();
            logger.log(Level.INFO, "path is " + baseUri+"+++"+fullUri);

            fullUri = "/"+fullUri.replace(baseUri, "");
            logger.log(Level.INFO, "path is " + fullUri);

            content = responseLoader.loadFile(responseLoader.getResponePath(fullUri)).toString();
        } catch (Exception ex) {

        }
        logger.log(Level.FINEST, "returning  " + content);
        return content;
    }

    @GET
    @Path("{subResources: [a-zA-Z0-9_/]*}")
    public String forwardMessage(@Context UriInfo uriInfo,@Context HttpHeaders httpHeaders)
    {

        try
        {
            getJNDIProperties ();
        }
        catch (Exception ex)
        {
            logger.log(Level.SEVERE,"fail to get JNDI exception");

        }

        if(host==null)
        {
            return "host not set, please set host name STUB_HOST under Eniq_Event_Properties  ";
        }

        Client client  = Client.create();
        String baseUri = uriInfo.getBaseUri().toASCIIString();
        String fullUri = uriInfo.getRequestUri().toASCIIString();
        fullUri =fullUri.replace(baseUri,"");
        String path="";

        if("".equals(applicationName))
        {
            if(host.charAt(host.length()-1)!='/')
            {
                path =    host+"/"+fullUri;
            }
            else
            {
                 path =    host+fullUri;
            }
        }
        else
        {
            if(host.charAt(host.length()-1)!='/')
            {
                path =host+"/"+applicationName+"/"+fullUri;
            }
            else
            {
                path = host +  applicationName+"/"+fullUri;
            }
        }

        path=path.replace(" ","");
        logger.log(Level.SEVERE,"Sending request(GET) to "+path);

        WebResource webResource = client.resource(path);
        String s=webResource.getRequestBuilder().get(String.class);
        logger.log(Level.SEVERE,"Result: "+s.substring(0,Math.min(s.length(),50))+"...");

        return s;

    }



}
