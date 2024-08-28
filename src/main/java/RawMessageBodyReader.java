import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public  class RawMessageBodyReader implements MessageBodyReader<ProxyRawType> {
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return false;
    }

    public ProxyRawType readFrom(Class<ProxyRawType> proxyRawTypeClass, Type type, Annotation[] annotations,
                                 MediaType mediaType, MultivaluedMap<String, String> stringStringMultivaluedMap,
                                 InputStream inputStream) throws IOException, WebApplicationException {
        BufferedReader brd  = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuffer sb=  new StringBuffer();
        while ((line =brd.readLine())!=null)
        {
            sb.append(line);
        }
        return new ProxyRawType(sb.toString());
    }

}