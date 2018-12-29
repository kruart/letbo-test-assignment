package letbo.interview.kruart.util;

import letbo.interview.kruart.AbstractTest;
import letbo.interview.kruart.model.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WordTest extends AbstractTest {

    @Test
    void testRandomWord() {
        String word = WordUtil.randomWord(config.getPathToFile());
        assertNotNull(word);
        assertEquals("dev", word);
    }

    @Test
    void testCreateWord() {
        String str = WordUtil.randomWord(config.getPathToFile());
        Word word = new Word(str, config.getMask());

        assertNotNull(word);
        assertEquals(str, String.valueOf(word.getLetters()));
        assertEquals(word.getLetters().length, word.getHiddenLetters().length);
        assertEquals(config.getMask(), word.getMask());
    }
}