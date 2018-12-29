package letbo.interview.kruart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${mask}")
    private String mask;

    @Value("${pathToFile}")
    private String pathToFile;

    public String getMask() {
        return mask;
    }

    public String getPathToFile() {
        return pathToFile;
    }
}
