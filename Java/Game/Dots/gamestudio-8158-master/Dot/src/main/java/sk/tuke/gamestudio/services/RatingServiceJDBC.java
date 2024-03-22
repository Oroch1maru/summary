package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

public class RatingServiceJDBC implements RatingService{

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String PASSWORD = "password228";
    public static final String NAME = "postgres";
    public static final String DELETE_STATEMENT = "DELETE FROM rating";
    public static final String UPDATE_STATEMENT="UPDATE rating SET stars=? WHERE player = ? AND game=?";
    public static final String ADDED_STATEMENT="INSERT INTO rating(player, game,stars, playedData) VALUES (?,?, ?, ?)";

    public static final String SELECT_PLAYER_STATEMENT = "SELECT stars FROM rating WHERE player = ? AND game = ?";

    public static final String SELECT_ALL_STARS_STATEMENT = "SELECT stars FROM rating WHERE game = ?";


    @Override
    public void setRating(Rating rating) {
        try(var connection = DriverManager.getConnection(JDBC_URL,NAME,PASSWORD);
            var statement = connection.prepareStatement(UPDATE_STATEMENT);
        ){
            statement.setInt(1,rating.getRating());
            statement.setString(2,rating.getPlayer());
            statement.setString(3,rating.getGame());
            if(statement.executeUpdate()==0){
                var newStatement = connection.prepareStatement(ADDED_STATEMENT);
                newStatement.setString(1, rating.getPlayer());
                newStatement.setString(2, rating.getGame());
                newStatement.setInt(3,rating.getRating());
                newStatement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
                newStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public int getAverageRating(String game) {
        try(var connection = DriverManager.getConnection(JDBC_URL,NAME,PASSWORD);
            var statement = connection.prepareStatement(SELECT_ALL_STARS_STATEMENT);
        ){
            statement.setString(1,game);
            try(var rs = statement.executeQuery()) {
                int average = 0;
                int count = 0;
                while (rs.next()) {
                    count++;
                    average += rs.getInt("stars");
                }
                if (count>0){
                    return average / count;
                }
                else{
                    return 0;
                }
            }
        }catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public int getRating(String game, String player) {
        try(var connection = DriverManager.getConnection(JDBC_URL,NAME,PASSWORD);
            var statement = connection.prepareStatement(SELECT_PLAYER_STATEMENT);
        ){
            statement.setString(1,player);
            statement.setString(2,game);
            try(var rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stars");
                } else {
                    return 0;
                }
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

