package sk.tuke.gamestudio.entity;

import java.util.Date;

public class Score {
    private String player;

    private String game;

    private int score;

    private Date playedData;

    public Score(String player, String game, int score, Date playedData) {
        this.player = player;
        this.game = game;
        this.score = score;
        this.playedData = playedData;
    }

    public String getPlayer() {
        return player;
    }

    public String getGame() {
        return game;
    }

    public int getScore() {
        return score;
    }

    public Date getPlayedData() {
        return playedData;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPlayedData(Date playedData) {
        this.playedData = playedData;
    }

    @Override
    public String toString() {
        return "Score{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", points=" + score +
                ", playedAt=" + playedData +
                '}';
    }
}
