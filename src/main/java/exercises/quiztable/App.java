package exercises.quiztable;

import oracle.jdbc.pool.OracleDataSource;

import java.io.File;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by guisil on 06/09/2016.
 */
public class App {

    public static void main(String[] args) {

        try {
            OracleDataSource dataSource = new OracleDataSource();
            dataSource.setDriverType("thin");
            dataSource.setServerName("localhost");
            dataSource.setNetworkProtocol("tcp");
            dataSource.setDatabaseName("test");
            dataSource.setPortNumber(1521);
            dataSource.setUser("test");
            dataSource.setPassword("test");
            dataSource.setServiceName("orcl");

            QuizDao dao = new QuizDao(dataSource);

            Map<String, Integer> tableContents = dao.getQuizTable();

            ScoreChecker scoreChecker = new ScoreChecker();
            Map<String, Integer> updatedContents = scoreChecker.checkScore(tableContents);

            dao.updateScore(updatedContents);

            Map<String, Integer> retrievedAgain = dao.getQuizTable();

            QuizConverter converter = new QuizConverter();
            converter.writeToCsvFile(retrievedAgain, new File("result.csv"));

        } catch(SQLException ex) {
            System.err.println("Database related problem: " + ex.getMessage());
        }
    }
}
