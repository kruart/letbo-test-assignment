package letbo.interview.kruart.util;

import letbo.interview.kruart.App;
import letbo.interview.kruart.TestController;
import letbo.interview.kruart.config.AppConfig;
import letbo.interview.kruart.model.Word;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = App.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class WordTest {

    @Autowired
    AppConfig config;

    @Autowired
    TestController t;

    @Test
    void testRandomWord() throws IOException {
        String word = WordUtil.randomWord(config.getPathToFile());
        assertNotNull(word);
        assertTrue(word.startsWith("developer"));
    }

    @Test
    void testCreateWord() throws IOException {
        String str = WordUtil.randomWord(config.getPathToFile());
        Word word = new Word(str, config.getMask());

        assertNotNull(word);
        assertEquals(str, String.valueOf(word.getLetters()));
        assertEquals(word.getLetters().length, word.getHiddenLetters().length);
        assertEquals(config.getMask(), word.getMask());
    }
}