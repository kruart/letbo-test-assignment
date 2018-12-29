package letbo.interview.kruart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping
    public String rootHello() {
        logger.debug("calling 'rootHello' method at path '/'");
        return "Hello, Java World!";
    }
}
