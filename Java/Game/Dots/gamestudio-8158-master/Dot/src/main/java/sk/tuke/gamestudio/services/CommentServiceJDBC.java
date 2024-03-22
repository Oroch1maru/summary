package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Comment;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {

    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String PASSWORD = "password228";
    public static final String NAME = "postgres";
    public static final String DELETE_STATEMENT = "DELETE FROM comment";
    public static final String ADDED_STATEMENT="INSERT INTO comment(player, game, feedback,playedData) VALUES (?,?,?,?)";
    public static final String SELECT_STATEMENT = "SELECT player , game , feedback , playedData FROM comment WHERE game = ?";


    @Override
    public void addComment(Comment comment) {
        try(var connection = DriverManager.getConnection(JDBC_URL,NAME,PASSWORD);
            var statement = connection.prepareStatement(ADDED_STATEMENT);
        ){
            statement.setString(1, comment.getPlayer());
            statement.setString(2, comment.getGame());
            statement.setString(3,comment.getFeedback());
            statement.setTimestamp(4, new Timestamp(comment.getCommentedOn().getTime()));
            statement.executeUpdate();
        }catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        try(var connection = DriverManager.getConnection(JDBC_URL,NAME,PASSWORD);
            var statement = connection.prepareStatement(SELECT_STATEMENT);
        ){
            statement.setString(1,game);
            try(var rs = statement.executeQuery()) {
                var topscore = new ArrayList<Comment>();
                while (rs.next()) {
                    topscore.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
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
