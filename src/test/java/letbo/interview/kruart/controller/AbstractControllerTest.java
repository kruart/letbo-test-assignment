package letbo.interview.kruart.controller;

import letbo.interview.kruart.AbstractTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
class AbstractControllerTest extends AbstractTest {

    @Autowired
    MockMvc mvc;
}
