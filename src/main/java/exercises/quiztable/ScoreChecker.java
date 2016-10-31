package exercises.quiztable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guisil on 06/09/2016.
 */
class ScoreChecker {

    Map<String, Integer> checkScore(Map<String, Integer> words) {
        Map<String, Integer> updatedScores = new HashMap<>();
        for (String word : words.keySet()) {
            updatedScores.put(word, countVowels(word));
        }
        return updatedScores;
    }

    private int countVowels(String word) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            String c = word.substring(i, i + 1);
            if (isVowel(c)) {
                count++;
            }
        }
        return count;
    }

    private boolean isVowel(String c) {
        if (c.equalsIgnoreCase("a") ||
                c.equalsIgnoreCase("e") ||
                c.equalsIgnoreCase("i") ||
                c.equalsIgnoreCase("o") ||
                c.equalsIgnoreCase("u")) {
            return true;
        }
        return false;
    }
}
