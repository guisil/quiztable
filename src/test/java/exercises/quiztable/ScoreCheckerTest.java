package exercises.quiztable;

import exercises.quiztable.ScoreChecker;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by guisil on 06/09/2016.
 */
public class ScoreCheckerTest {

    private ScoreChecker scoreChecker;

    @Before
    public void setUp() throws Exception {
        scoreChecker = new ScoreChecker();
    }

    @Test
    public void checkScore() throws Exception {

        Map<String, Integer> example = new HashMap<>();
        example.put("QhBojknNtJNj", 0);
        example.put("nN9cTbXOsllU", 0);
        example.put("kcoMag7UbVDB", 0);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("QhBojknNtJNj", 1);
        expected.put("nN9cTbXOsllU", 2);
        expected.put("kcoMag7UbVDB", 3);

        Map<String, Integer> result = scoreChecker.checkScore(example);

        assertEquals("Score results different from expected", expected, result);
    }

}