package sk.tuke.gamestudio.entity;

import java.util.Date;

public class Comment {
    private String player;

    private String game;

    private String feedback;

    private Date commentedOn;

    public Comment(String player, String game, String feedback, Date commentedOn) {
        this.player = player;
        this.game = game;
        this.feedback = feedback;
        this.commentedOn = commentedOn;
    }

    public String getPlayer() {
        return player;
    }

    public String getGame() {
        return game;
    }

    public String getFeedback() {
        return feedback;
    }

    public Date getCommentedOn() {
        return commentedOn;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setCommentedOn(Date commentedOn) {
        this.commentedOn = commentedOn;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", feedback=" + feedback +
                ", playedAt=" + commentedOn +
                '}';
    }
}

