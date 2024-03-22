package sk.tuke.gamestudio.services;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Score;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreServiceTest {
    private ScoreService scoreService = new ScoreServiceJDBC();

    @Test
    public void testReset(){
        scoreService.reset();
        assertEquals(0,scoreService.getTopScore("dots").size());
    }

    @Test
    public void testAdd(){
        scoreService.reset();
        scoreService.addScore(new Score("Dania","dots",52,new Date()));

        assertEquals(1,scoreService.getTopScore("dots").size());
        assertEquals(52, scoreService.getTopScore("dots").get(0).getScore());
    }

    @Test
    public void testGetTopScores(){
        scoreService.reset();
        scoreService.addScore(new Score("Dania","dots",52,new Date()));
        scoreService.addScore(new Score("Oleh","dots",64,new Date()));
        scoreService.addScore(new Score("Diana","mines",41,new Date()));

        assertEquals(2,scoreService.getTopScore("dots").size());
        assertEquals(1,scoreService.getTopScore("mines").size());
        assertEquals(64, scoreService.getTopScore("dots").get(0).getScore());
        assertEquals("Dania", scoreService.getTopScore("dots").get(1).getPlayer());
    }
}
