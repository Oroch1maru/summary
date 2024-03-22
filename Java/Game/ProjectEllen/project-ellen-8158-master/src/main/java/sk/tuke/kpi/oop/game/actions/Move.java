package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;

public class Move<A extends Movable> implements Action<A> {
    private Direction direction;
    private float duration;
    private boolean check;

    private A actor;

    private int movingTime;


    public Move(Direction direction, float duration) {
        this.direction = direction;
        this.duration = duration;
        check = false;
        movingTime = 0;

    }
    @Override
    public boolean isDone() {
        return check;
    }

    @Override
    public void setActor(@Nullable A actor) {
        this.actor=actor;
    }

    @Nullable
    @Override
    public A getActor() {
        return actor;
    }

    @Override
    public void reset() {
        duration=0;
        check=false;
        movingTime=0;
        actor.stoppedMoving();
    }


    @Override
    public void execute(float deltaTime) {
        if(this.actor!=null && isDone()==false){
            duration = duration - deltaTime;
            if(movingTime==0){
                actor.startedMoving(direction);
                movingTime+=1;
            }
            if(duration>0){
                actor.setPosition(actor.getPosX() + direction.getDx() * actor.getSpeed(), actor.getPosY() + direction.getDy() * actor.getSpeed());
                if ((getActor().getScene()).getMap().intersectsWithWall(actor)) {
                    actor.setPosition(actor.getPosX() - direction.getDx() * actor.getSpeed(), actor.getPosY() - direction.getDy() * actor.getSpeed());
                    actor.collidedWithWall();
                }
            }
            else{
                stop();
            }
        }
    }

    public void stop(){
        if(this.actor!=null){
            check=true;
            actor.stoppedMoving();
        }
    }
}
