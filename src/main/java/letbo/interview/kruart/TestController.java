package letbo.interview.kruart;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class TestController {

    @GetMapping
    public String rootHello() {
        return "Hello, Java World!";
    }
}
