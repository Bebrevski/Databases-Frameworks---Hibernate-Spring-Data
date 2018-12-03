package judgesystem.service;

import java.io.IOException;

public interface StrategyService {
    String importStrategies(String strategiesFilePath) throws IOException;
}
