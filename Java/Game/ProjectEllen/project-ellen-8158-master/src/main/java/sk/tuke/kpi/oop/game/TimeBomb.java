package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private float timeUp=0.0f;
    private boolean check_on=false;
    private Animation bomb_on;
    private Animation bomb_boom=new Animation("sprites/small_explosion.png",16,16,0.2f, Animation.PlayMode.ONCE);

    public TimeBomb(float time){
        this.timeUp=time;
        setAnimation(
            new Animation("sprites/bomb.png")
        );
        bomb_on=new Animation("sprites/bomb_activated.png",16,16,timeUp/6f, Animation.PlayMode.ONCE);
    }

    public void activate(){
        this.check_on=true;
        setAnimation(bomb_on);
        new ActionSequence<>(
            new Wait<>(timeUp),
            new Loop<>(new Invoke<Actor>(this::boom))
        ).scheduleFor(this);
    }


    public boolean isActivated(){
        return this.check_on;
    }

    protected void boom(){
        setAnimation(bomb_boom);
        new ActionSequence<>(
            new Wait<>(1.6f),
            new Loop<>(new Invoke<Actor>(this::delete))
        ).scheduleFor(this);
    }
    private void delete(){
        this.check_on=false;
        getScene().removeActor(this);
    }


}
