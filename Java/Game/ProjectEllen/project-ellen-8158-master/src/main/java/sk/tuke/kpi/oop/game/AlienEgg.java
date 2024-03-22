package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Follow;
import sk.tuke.kpi.oop.game.characters.*;

import java.awt.geom.Ellipse2D;

public class AlienEgg extends AbstractActor implements Alive {
    private Animation normEgg=new Animation("sprites/alien_egg.png",32,32, 0.5f, Animation.PlayMode.ONCE);
    private Ellipse2D.Float ellipse;
    private Disposable dispos;
    private boolean checkOn=false;
    private Ripley foundRipley;
    private Lurker lurker;
    private Health health;
    public AlienEgg(){
        setAnimation(normEgg);
        getAnimation().stop();
        health=new Health(500);
        health.onFatigued(() -> {
            getScene().removeActor(this);
        });
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        ellipse = new Ellipse2D.Float(this.getPosX()-16,this.getPosY()-16,100,100);
        dispos = new Loop<>(
            new Invoke<>(this::check)
        ).scheduleFor(this);

        new When<>(
            () -> checkOn == true,
            new ActionSequence<>(
                new Wait<>(1f),
                new Invoke<>(this::spawn)
            )
        ).scheduleFor(this);

    }
    public void check(){
        foundRipley=this.getScene().getFirstActorByType(Ripley.class);
        if(foundRipley==null){
            return;
        }
        Ellipse2D.Float secondEllipse = new Ellipse2D.Float(foundRipley.getPosX(),foundRipley.getPosY(),32,32);
        if(ellipse.intersects(secondEllipse.getBounds2D())){
            checkOn=true;
            getAnimation().play();
            dispos.dispose();
        }
    }
    public void spawn(){
        this.getScene().removeActor(this);
        lurker=new Lurker( new Follow());
        this.getScene().addActor(lurker, this.getPosX(), this.getPosY());
        new Loop<>(
            new ActionSequence<>(
                new Wait<>(0.5f),
                new Invoke<>(this::attack)
            )
        ).scheduleFor(lurker);
    }

    public void attack(){

        if(lurker.intersects(foundRipley)){
            foundRipley.getHealth().drain(50);
        }
    }

    @Override
    public Health getHealth() {
        return health;
    }

}
