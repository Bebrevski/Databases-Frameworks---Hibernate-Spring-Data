package app.retake.parser;

import app.retake.parser.interfaces.Parser;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component(value = "JSONParser")
public class JSONParser implements Parser {

    @Override
    public <T> T read(Class<T> objectClass, String fileContent) throws IOException {
        return null;
    }

    @Override
    public <T> String write(T object) throws IOException {
        return null;
    }
}
