package sk.tuke.kpi.oop.game.securityroom;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;

import java.util.List;

public class SecurityItem extends AbstractActor implements Security {
    protected boolean checkOn=false;
    protected Animation lazerOn;
    public SecurityItem(Animation lazerOn){
        this.lazerOn=lazerOn;
        setAnimation(lazerOn);
    }


    public void turnOn(){
        setAnimation(lazerOn);
        checkOn=true;
    }

    public void turnOff(){
        getAnimation().resetToFirstFrame();
        getAnimation().stop();
        checkOn=false;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::attack),
                new Wait<>(0.3f)
            )
        ).scheduleFor(this);
    }

    public void attack(){
        if(checkOn==true) {
            getAnimation().play();
            List<Actor> alives;
            alives = getScene().getActors();
            for (Actor alive : alives) {
                if (alive instanceof Alive && this.intersects(alive)) {
                    ((Alive) alive).getHealth().drain(500);
                }
            }
        }
    }

    @Override
    public void setPowered(boolean energy) {
        checkOn=energy;
        if(this.checkOn==false){
            turnOff();
        }
        else if(checkOn==true){
            turnOn();
        }
    }
}
