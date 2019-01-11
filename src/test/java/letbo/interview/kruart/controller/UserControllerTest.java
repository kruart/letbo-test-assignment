package letbo.interview.kruart.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import letbo.interview.kruart.to.UserTo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractControllerTest {

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