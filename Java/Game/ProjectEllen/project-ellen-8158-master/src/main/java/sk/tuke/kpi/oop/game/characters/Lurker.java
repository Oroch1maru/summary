package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Lurker extends AbstractActor implements Enemy, Alive,Movable {
    private int speed=7;
    private Behaviour<? super Lurker> behaviour=null;
    private Animation LurkerAnimation= new Animation("sprites/lurker_alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);;
    private Health health;
    public Lurker(Behaviour<? super Lurker> behaviour){
        setAnimation(LurkerAnimation);
        health=new Health(10);
        health.onFatigued(() -> {
            getScene().removeActor(this);
        });
        this.behaviour=behaviour;
    }


    @Override
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed){
        this.speed=speed;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (behaviour != null) {
            behaviour.setUp(this);
        }
    }

}
