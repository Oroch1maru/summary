package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.awt.geom.Ellipse2D;
import java.util.List;

public class Mine extends AbstractActor{
    private Disposable dispos;
    public Mine() {
        setAnimation(
            new Animation("sprites/mine.png",16,16,0.3f, Animation.PlayMode.ONCE)
        );
        getAnimation().stop();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        dispos = new Loop<>(
            new Invoke<>(this::check)
        ).scheduleFor(this);
    }

    public void check() {
        Ripley foundRipley=this.getScene().getFirstActorByType(Ripley.class);
        if (this.intersects(foundRipley)) {
            getAnimation().play();
            new ActionSequence<>(
                new Wait<>(0.9f),
                new Invoke<>(()->setAnimation(new Animation("sprites/large_explosion.png",32,32,0.2f, Animation.PlayMode.ONCE))),
                new Invoke<Actor>(this::dead),
                new Wait<>(1.6f),
                new Invoke<>(()->this.getScene().removeActor(this))
            ).scheduleFor(this);
            dispos.dispose();
        }
    }
    public void dead(){
        getAnimation().play();
        List<Actor> alives;
        alives = getScene().getActors();
        Ellipse2D.Float ellipse = new Ellipse2D.Float(this.getPosX()-8,this.getPosY()-8,50,50);
        for (Actor alive : alives) {
            if(alive instanceof Alive){
                Ellipse2D.Float secondEllipse = new Ellipse2D.Float(alive.getPosX(),alive.getPosY(),alive.getWidth(),alive.getHeight());
                if(ellipse.intersects(secondEllipse.getBounds2D())){
                    ((Alive) alive).getHealth().drain(500);
                }
            }
        }
    }
}
