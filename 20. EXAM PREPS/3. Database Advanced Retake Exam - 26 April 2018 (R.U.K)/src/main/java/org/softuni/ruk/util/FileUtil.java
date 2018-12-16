package org.softuni.ruk.util;

import java.io.IOException;

public interface FileUtil {

    String readFile(String filePath) throws IOException;

    //void write(String fileContent, String file) throws IOException;
}
