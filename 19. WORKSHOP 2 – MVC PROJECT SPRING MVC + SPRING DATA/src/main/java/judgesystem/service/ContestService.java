package judgesystem.service;

import java.io.IOException;

public interface ContestService {
    String importContests(String contestsFilePath) throws IOException;

}
