package letbo.interview.kruart;

import letbo.interview.kruart.config.AppConfig;
import letbo.interview.kruart.to.PlayerTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = App.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AbstractTest {

    @Autowired
    protected AppConfig config;

    protected PlayerTO doe = new PlayerTO("John Doe", "e");
    protected PlayerTO roe = new PlayerTO("Richard Roe", "f");
}
