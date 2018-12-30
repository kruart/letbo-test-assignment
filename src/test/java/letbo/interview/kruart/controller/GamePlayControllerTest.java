package letbo.interview.kruart.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import letbo.interview.kruart.AbstractTest;
import letbo.interview.kruart.App;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = App.class)
@AutoConfigureMockMvc
class GamePlayControllerTest extends AbstractTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private GamePlayController controller;

    @BeforeEach
    void setUp() {
        controller.getGamePlay().newGame();
    }


    @Test
    void testStart() throws Exception {
        mvc.perform(post("/game/start"))
                .andExpect(status().isOk())
                .andExpect(content().string("Requires at least 1 player! Please register!"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    void testNewGame() throws Exception {
        controller.register(doe);
        controller.start();

        mvc.perform(post("/game/new"))
                .andExpect(status().isOk())
                .andExpect(content().string("Requires at least 1 player! Please register!"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }


    @Test
    void testRegister() throws Exception {
        mvc.perform(post("/game/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(doe)))
                .andExpect(status().isOk())
                .andExpect(content().string("John Doe has just registered! We're already have 1 player(s)!"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    void testMove() throws Exception {
        controller.register(doe);
        controller.start();

        mvc.perform(put("/game/move")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(doe)))
                .andExpect(status().isOk())
                .andExpect(content().string("Wow! You've guessed! Move again. Guess the letter: *e*"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    void testGetWord() throws Exception {
        controller.register(doe);
        controller.start();

        mvc.perform(get("/game/word"))
                .andExpect(status().isOk())
                .andExpect(content().string("***"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    void testGetPlayers() throws Exception {
        controller.register(doe);
        controller.register(roe);

        mvc.perform(get("/game/players"))
                .andExpect(status().isOk())
                .andExpect(content().json(writeValue(controller.getGamePlay().getPlayers())))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    public static <T> String writeValue(T obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }
}