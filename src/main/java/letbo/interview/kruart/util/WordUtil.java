package letbo.interview.kruart.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class WordUtil {

    /**
     * Get random word from file. The file must be in the resource folder
     * @param path to file
     */
    public static String randomWord(String path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return lines.get(new Random().nextInt(lines.size()));
    }
}
