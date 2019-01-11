package letbo.interview.kruart.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import letbo.interview.kruart.App;
import letbo.interview.kruart.to.UserTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void testSignInSuccess() throws Exception {
        mvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(new UserTo("Richard Roe", "qwerty"))))
                .andExpect(status().isOk());
    }

    @Test
    void testSignInFailed() throws Exception {
        mvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(new UserTo("Richard Roe", "failed"))))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Bad Credentials!"));
    }

    private static <T> String writeValue(T obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }
}