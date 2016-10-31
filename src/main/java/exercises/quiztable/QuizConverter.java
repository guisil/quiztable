package exercises.quiztable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by guisil on 06/09/2016.
 */
class QuizConverter {

    void writeToCsvFile(Map<String, Integer> words, File outputFile) {

        try (FileWriter writer = new FileWriter(outputFile)) {

            writer.write("\"QUIZ_WORD\",\"QUIZ_SCORE\"\n");

            for (String word : words.keySet()) {
                writer.write("\"" + word + "\"" + "," + words.get(word) + "\n");
            }

            writer.flush();

        } catch (IOException ex) {
            System.err.println("Error writing to csv file: " + ex.getMessage());
        }
    }
}
