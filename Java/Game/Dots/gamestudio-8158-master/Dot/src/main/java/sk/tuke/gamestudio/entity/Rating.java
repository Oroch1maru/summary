package sk.tuke.gamestudio.entity;

import java.util.Date;

public class Rating {
    private String player;

    private String game;

    private int rating;

    private Date ratedOn;

    public Rating(String player, String game, int rating, Date ratedOn) {
        this.player = player;
        this.game = game;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    public String getPlayer() {
        return player;
    }

    public String getGame() {
        return game;
    }

    public int getRating() {
        return rating;
    }

    public Date getRatedOn() {
        return ratedOn;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRatedOn(Date ratedOn) {
        this.ratedOn = ratedOn;
    }

    @Override
    public String toString() {
        return "rating{" +
                "player='" + player + '\'' +
                ", game='" + game + '\'' +
                ", rating=" + rating +
                ", ratedOn=" + ratedOn +
                '}';
    }
}
