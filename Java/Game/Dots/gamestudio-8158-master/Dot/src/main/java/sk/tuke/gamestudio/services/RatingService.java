package sk.tuke.gamestudio.services;

import sk.tuke.gamestudio.entity.Rating;

public interface RatingService {
    void setRating(Rating rating);

    int getAverageRating(String game);

    int getRating(String game, String player);
    void reset();
}
