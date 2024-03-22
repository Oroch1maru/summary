package sk.tuke.gamestudio.services;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class RatingServiceTest {
    private RatingService ratingService = new RatingServiceJDBC();
    @Test
    public void testSetRating() {
        ratingService.reset();
        ratingService.setRating(new Rating("Dania","dots",1,new Date()));
        ratingService.setRating(new Rating("Max","dots",3,new Date()));
        ratingService.setRating(new Rating("Oleh","dots",4,new Date()));
        ratingService.setRating(new Rating("Diana","dots",1,new Date()));
        ratingService.setRating(new Rating("Dania","dots",4,new Date()));
        assertEquals(3,ratingService.getAverageRating("dots"));
        assertEquals(0,ratingService.getAverageRating("mines"));
    }

    @Test
    public void testGetAverageRating() {
        ratingService.reset();
        ratingService.setRating(new Rating("Dania","dots",4,new Date()));
        ratingService.setRating(new Rating("Max","dots",3,new Date()));
        ratingService.setRating(new Rating("Oleh","dots",4,new Date()));
        ratingService.setRating(new Rating("Diana","dots",1,new Date()));
        assertEquals(3,ratingService.getAverageRating("dots"));
    }

    @Test
    public void testGetRating() {
        ratingService.reset();
        ratingService.setRating(new Rating("Dania","dots",4,new Date()));
        assertEquals(4,ratingService.getRating("dots","Dania"));
    }

    @Test
    public void testReset() {
        ratingService.reset();
        ratingService.setRating(new Rating("Dania","dots",4,new Date()));
        ratingService.reset();
        assertEquals(0,ratingService.getAverageRating("dots"));
    }


}