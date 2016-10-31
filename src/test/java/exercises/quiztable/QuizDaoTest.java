package exercises.quiztable;

import exercises.quiztable.QuizDao;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by guisil on 06/09/2016.
 */
public class QuizDaoTest {

    private String dbUrl = "jdbc:h2:mem:test";
    private final Connection testConnection;

    private QuizDao dao;

    public QuizDaoTest() throws SQLException {
        testConnection = DriverManager.getConnection(dbUrl);

        assertNotNull(testConnection);
    }

    @Before
    public void setUp() throws Exception {

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(dbUrl);

        dao = new QuizDao(ds);

        setOracleModeInDb();

        createTableInDb();
        insertDataInDb();
    }

    @After
    public void tearDown() throws Exception {
        dropTableInDb();
    }

    @Test
    public void getQuizTable() throws Exception {
        Map<String, Integer> expected = getExpectedMap();
        Map<String, Integer> result = dao.getQuizTable();
        assertEquals("Result different from expected", expected, result);
    }

    @Test
    public void updateScore() throws Exception {
        Map<String, Integer> expected = getExpectedMapWithScores();
        dao.updateScore(expected);
        Map<String, Integer> actual = selectAllWordsInDb();
        assertEquals("Scores were not updated", expected, actual);
    }

    private void setOracleModeInDb() throws SQLException {
        String setMode = "SET MODE Oracle";
        try (Statement statement = testConnection.createStatement()) {
            statement.execute(setMode);
        }
    }

    private void createTableInDb() throws SQLException {
        String createQuery = "CREATE TABLE QUIZ_TABLE (" +
                " QUIZ_WORD VARCHAR2(12), " +
                " QUIZ_SCORE NUMBER(2) " +
                ")";
        try (Statement statement = testConnection.createStatement()) {
            statement.executeUpdate(createQuery);
        }
    }

    private void insertDataInDb() throws SQLException {

        List<String> inserts = new ArrayList<>();
        inserts.add("INSERT INTO QUIZ_TABLE VALUES ('QhBojknNtJNj', 0)");
        inserts.add("INSERT INTO QUIZ_TABLE VALUES ('nN9cTbXQsllU', 0)");
        inserts.add("INSERT INTO QUIZ_TABLE VALUES ('kcoMng7YbVDB', 0)");

        try (Statement statement = testConnection.createStatement()) {
            for (String insert : inserts) {
                statement.executeUpdate(insert);
            }
        }
    }

    private void dropTableInDb() throws SQLException {
        String dropTable = "DROP TABLE QUIZ_TABLE";
        try (Statement statement = testConnection.createStatement()) {
            statement.executeUpdate(dropTable);
        }
    }

    private Map<String, Integer> selectAllWordsInDb() throws SQLException {
        String selectQuery = "SELECT QUIZ_WORD, QUIZ_SCORE " +
                "FROM QUIZ_TABLE";
        Map<String, Integer> allWords = new HashMap<>();
        try (Statement statement = testConnection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(selectQuery);
            while(resultSet.next()) {
                allWords.put(
                        resultSet.getString("QUIZ_WORD"),
                        resultSet.getInt("QUIZ_SCORE")
                        );
            }
        }
        return allWords;
    }

    private Map<String, Integer> getExpectedMap() {
        Map<String, Integer> toReturn = new HashMap<>();
        toReturn.put("QhBojknNtJNj", 0);
        toReturn.put("nN9cTbXQsllU", 0);
        toReturn.put("kcoMng7YbVDB", 0);
        return toReturn;
    }

    private Map<String, Integer> getExpectedMapWithScores() {
        Map<String, Integer> toReturn = new HashMap<>();
        toReturn.put("QhBojknNtJNj", 1);
        toReturn.put("nN9cTbXQsllU", 1);
        toReturn.put("kcoMng7YbVDB", 1);
        return toReturn;
    }
}