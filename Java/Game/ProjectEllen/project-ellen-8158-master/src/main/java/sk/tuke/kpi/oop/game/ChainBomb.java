package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.Actor;
import java.awt.geom.Ellipse2D;
import java.util.List;

public class ChainBomb extends TimeBomb {


    public ChainBomb(float time) {
        super(time);
    }

    @Override
    public void boom() {
        System.out.println(this.getPosX()-(this.getWidth()/2));
        System.out.println(this.getPosY()-(this.getHeight()/2));
        super.boom();
        List<Actor> actors = getScene().getActors();
        Ellipse2D.Float ellipse = new Ellipse2D.Float(this.getPosX()-42,this.getPosY()-42,100,100);
        System.out.println(ellipse.getCenterX());
        System.out.println(ellipse.getCenterY());
        for (Actor actor : actors){
            if(actor.getName().equals("ChainBomb") && actor!=this){
                Ellipse2D.Float SecondEllipse = new Ellipse2D.Float(actor.getPosX(),actor.getPosY(),16,16);
                if(ellipse.intersects(SecondEllipse.getBounds2D())){
                    ((ChainBomb) actor).activate();
                }
            }
        }
    }

}
