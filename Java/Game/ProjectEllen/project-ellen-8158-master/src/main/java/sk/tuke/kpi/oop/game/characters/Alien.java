package sk.tuke.kpi.oop.game.characters;


import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;


public class Alien extends AbstractActor implements Enemy, Alive,Movable {
    private Animation AlienAnimation;
    protected Behaviour<? super Alien> behaviour=null;
    private Health health;
    private int speed=3;
    private int damage=3;
    public Alien(){
        AlienAnimation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(AlienAnimation);
        health=new Health(100);
        health.onFatigued(() -> {
            getScene().removeActor(this);
        });
    }
    public Alien(int healthValue, Behaviour<? super Alien> behaviour){
        AlienAnimation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(AlienAnimation);
        health=new Health(healthValue);
        health.onFatigued(() -> {
            getScene().removeActor(this);
        });
        this.behaviour=behaviour;
    }
    @Override
    public int getSpeed() {
        return speed;
    }



    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(this::decrease),
                    new Wait<>(1f)
                )
        ).scheduleFor(this);

    }
    public void decrease(){
        if(behaviour!=null){
            behaviour.setUp(this);
        }
        List<Actor> aliveActorsList;
        aliveActorsList = getScene().getActors();

        for (Actor alive : aliveActorsList) {
            if (alive instanceof Alive && !(alive instanceof Enemy) && this.intersects(alive)) {

                ((Alive) alive).getHealth().drain(damage);

            }
        }
    }

    protected void setDamage(int newDamage){
        this.damage=newDamage;
    }


}

