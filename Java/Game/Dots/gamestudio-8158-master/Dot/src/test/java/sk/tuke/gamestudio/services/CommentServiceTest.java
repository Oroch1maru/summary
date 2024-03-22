package sk.tuke.gamestudio.services;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceTest {
    private CommentService commentService = new CommentServiceJDBC();

    @Test
    public void testReset(){
        commentService.reset();
        commentService.addComment(new Comment("Dania","dots","Good game",new Date()));
        commentService.reset();
        assertEquals(0,commentService.getComments("dots").size());
    }

    @Test
    public void testAddComment(){
        commentService.reset();
        commentService.addComment(new Comment("Dania","dots","Good game",new Date()));
        commentService.addComment(new Comment("Diana","dots","Bad game",new Date()));
        commentService.addComment(new Comment("Oleh","mines","gdgdsggsgsvestgertecvs",new Date()));
        assertEquals(2,commentService.getComments("dots").size());
        assertEquals("Bad game",commentService.getComments("dots").get(1).getFeedback());
        assertEquals(1,commentService.getComments("mines").size());
    }

    @Test
    public void testGetComments(){
        commentService.reset();
        commentService.addComment(new Comment("Dania","dots","Good game",new Date()));
        commentService.addComment(new Comment("Diana","dots","Bad game",new Date()));
        commentService.addComment(new Comment("Max","dots","gdgsedgeaxaaQge",new Date()));
        commentService.addComment(new Comment("Oleh","mines","gdgdsggsgsvestgertecvs",new Date()));
        assertEquals(3,commentService.getComments("dots").size());
        assertEquals("Dania",commentService.getComments("dots").get(0).getPlayer());
        assertEquals("Bad game",commentService.getComments("dots").get(1).getFeedback());
        assertEquals("dots",commentService.getComments("dots").get(2).getGame());
        assertEquals(1,commentService.getComments("mines").size());
    }


}
