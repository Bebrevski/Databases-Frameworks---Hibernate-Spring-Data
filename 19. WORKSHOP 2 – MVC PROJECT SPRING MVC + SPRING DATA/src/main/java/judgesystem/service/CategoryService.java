package judgesystem.service;

import java.io.IOException;

public interface CategoryService {
    String importCategories(String categoriesFilePath) throws IOException;
}
