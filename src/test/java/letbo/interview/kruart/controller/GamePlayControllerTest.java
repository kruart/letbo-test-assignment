package letbo.interview.kruart.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import letbo.interview.kruart.to.GameInfoTo;
import letbo.interview.kruart.to.PlayerTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static letbo.interview.kruart.util.Messages.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GamePlayControllerTest  extends AbstractControllerTest {

    @Autowired
    private GamePlayController controller;

    @BeforeEach
    void setUp() {
        controller.getGamePlay().newGame();
    }

    @Test
    void testStart() throws Exception {
        mvc.perform(post("/game/start").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(writeValue(gameInfo(NO_PLAYERS))))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void testNewGame() throws Exception {
        controller.register(doe);
        controller.start();

        mvc.perform(post("/game/new"))
                .andExpect(status().isOk())
                .andExpect(content().json(writeValue(gameInfo(NEW_GAME))))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }


    @Test
    void testRegister() throws Exception {
        mvc.perform(post("/game/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(doe)))
                .andExpect(status().isOk())
                .andExpect(content().json(writeValue(gameInfo(String.format(PLAYER_REGISTERED_FMT, "John Doe")))))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void testMove() throws Exception {
        controller.register(doe);
        controller.start();

        mvc.perform(put("/game/move")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(doe)))
                .andExpect(status().isOk())
                .andExpect(content().json(writeValue(gameInfo(GUESSED))))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
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

    @Test
    void testRegisterNullName() throws Exception {
        PlayerTo p = doe;
        p.setName(null);

        mvc.perform(post("/game/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(doe)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    @Test
    void testMoveNameAndLetterNulls() throws Exception {
        controller.register(doe);
        controller.start();
        PlayerTo p = new PlayerTo(null, null);

        mvc.perform(put("/game/move")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(p)))
                .andExpect(status().isUnprocessableEntity())
//                .andExpect(content().string("The name cannot be null and The letter cannot be null")) //random, not stable
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
    }

    private static <T> String writeValue(T obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    private GameInfoTo gameInfo(String message) {
        return controller.getGamePlay().gameInfo(message);
    }
}