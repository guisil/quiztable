package exercises.quiztable;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by guisil on 06/09/2016.
 */
class QuizDao {

    private DataSource dataSource;

    QuizDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    Map<String, Integer> getQuizTable() {
        String selectQuery = "SELECT QUIZ_WORD, QUIZ_SCORE " +
                "FROM QUIZ_TABLE " +
                "ORDER BY QUIZ_SCORE DESC";
        Map<String, Integer> result = new LinkedHashMap<>();
        try (Connection dbConnection = dataSource.getConnection();
             Statement selectStatement = dbConnection.createStatement()) {

            // the fetch size can be specified in order to not load the whole ResultSet at once into memory
            selectStatement.setFetchSize(100);
            ResultSet resultSet = selectStatement.executeQuery(selectQuery);

            while (resultSet.next()) {
                result.put(resultSet.getString("QUIZ_WORD"), resultSet.getInt("QUIZ_SCORE"));
            }
        } catch(SQLException ex) {
            System.err.println("SQL State: " + ex.getSQLState());
            System.err.println("Error code: " + ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
        return result;
    }

    void updateScore(Map<String, Integer> updated) {
        String updateQuery = "UPDATE QUIZ_TABLE " +
                "SET QUIZ_SCORE = ? " +
                "WHERE QUIZ_WORD LIKE ?";
        try (Connection dbConnection = dataSource.getConnection();
             PreparedStatement updateStatement = dbConnection.prepareStatement(updateQuery)) {

            dbConnection.setAutoCommit(false);
            for (String word : updated.keySet()) {
                updateStatement.setInt(1, updated.get(word));
                updateStatement.setString(2, word);
                updateStatement.addBatch();
            }
            updateStatement.executeBatch();
            dbConnection.commit();
            dbConnection.setAutoCommit(true);
        } catch(SQLException ex) {
            System.err.println("SQL State: " + ex.getSQLState());
            System.err.println("Error code: " + ex.getErrorCode());
            System.err.println(ex.getMessage());
        }
    }
}
