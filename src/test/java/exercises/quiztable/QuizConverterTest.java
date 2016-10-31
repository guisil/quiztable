package exercises.quiztable;

import exercises.quiztable.QuizConverter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by guisil on 06/09/2016.
 */
public class QuizConverterTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private QuizConverter converter;

    @Before
    public void setUp() throws Exception {
        converter = new QuizConverter();
    }

    @Test
    public void convertToCsv() throws Exception {
        Map<String, Integer> example = new HashMap<>();
        example.put("QhBojknNtJNj", 1);
        example.put("nN9cTbXOsllU", 2);
        example.put("kcoMag7UbVDB", 3);

        String expected = "\"QUIZ_WORD\",\"QUIZ_SCORE\"\n" +
                "\"QhBojknNtJNj\",1\n" +
                "\"nN9cTbXOsllU\",2\n" +
                "\"kcoMag7UbVDB\",3\n";

        File testFile = tempFolder.newFile("test.csv");

        converter.writeToCsvFile(example, testFile);

        assertTrue(testFile.exists());

        String actual = readFile(testFile);
        assertEquals("Retrieved file content different from expected", expected, actual);
    }

    private String readFile(File file) {

        String loaded = "";

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                loaded = loaded + line + "\n";
            }

        } catch (IOException ex) {
            System.err.println("Error loading file: " + ex.getMessage());
        }

        return loaded;
    }
}