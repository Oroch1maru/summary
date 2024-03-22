package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Score;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ScoreServiceJDBC implements ScoreService{

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String PASSWORD = "password228";
    public static final String NAME = "postgres";
    public static final String DELETE_STATEMENT = "DELETE FROM score";
    public static final String ADDED_STATEMENT="INSERT INTO score(player, game,points, playedData) VALUES (?,?, ?, ?)";
    public static final String SELECT_STATEMENT = "SELECT player , game , points , playedData FROM score WHERE game = ? ORDER BY points DESC LIMIT 10";


    @Override
    public void addScore(Score score) {
        try(var connection = DriverManager.getConnection(JDBC_URL,NAME,PASSWORD);
            var statement = connection.prepareStatement(ADDED_STATEMENT);
        ){
            statement.setString(1, score.getPlayer());
            statement.setString(2, score.getGame());
            statement.setInt(3,score.getScore());
            statement.setTimestamp(4, new Timestamp(score.getPlayedData().getTime()));
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Score> getTopScore(String game) {
        try(var connection = DriverManager.getConnection(JDBC_URL,NAME,PASSWORD);
            var statement = connection.prepareStatement(SELECT_STATEMENT);
        ){
            statement.setString(1,game);
            try(var rs = statement.executeQuery()) {
                var topscore = new ArrayList<Score>();
                while (rs.next()) {
                    topscore.add(new Score(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(4)));

                }
                return topscore;
            }
        }catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void reset() {
        try(var connection = DriverManager.getConnection(JDBC_URL,NAME,PASSWORD);
            var statement = connection.createStatement()
            ){
            statement.executeUpdate(DELETE_STATEMENT);
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}
