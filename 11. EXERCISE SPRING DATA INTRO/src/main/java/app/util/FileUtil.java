package app.util;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileUtil {
    String[] getFileContent(String path) throws IOException;
}
