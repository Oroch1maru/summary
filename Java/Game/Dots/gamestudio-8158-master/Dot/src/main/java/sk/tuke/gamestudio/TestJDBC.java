package sk.tuke.gamestudio;

import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.services.*;
import sk.tuke.gamestudio.services.ScoreService;
import sk.tuke.gamestudio.services.ScoreServiceJDBC;

import java.util.Date;

public class TestJDBC {
    public static void main(String[] args) throws Exception{
        ScoreService scoreService=new ScoreServiceJDBC();
        scoreService.addScore(new Score("Dan","dots",53,new Date()));
//        scoreService.reset();
//        var list=scoreService.getTopScore("dots");
//        System.out.println(list);

    }
}
