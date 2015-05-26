import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author: William Navey
 */
public class JsonReader<T> {

    public T deserializeJsonFile(String jsonFilepath, Class<T> jsonClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Reader jsonReader =
                new InputStreamReader(getClass().getResourceAsStream(jsonFilepath));
        return mapper.readValue(jsonReader, jsonClass);
    }
}
