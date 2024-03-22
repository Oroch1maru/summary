package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Random;


public class RandomlyMoving implements Behaviour<Movable>{
    public void randomMove(Movable movable){
        int posX = new Random().nextInt(3) - 1;
        int posY = new Random().nextInt(3) - 1;
        Direction direction=Direction.NONE;
        for (Direction value : Direction.values()) {
            if (value.getDx() == posX && value.getDy() == posY)
            {
                direction=value;
            }
        }
        System.out.println(direction);
        movable.getAnimation().setRotation(direction.getAngle());
        new Move<>(direction, 3).scheduleFor(movable);
    }
    @Override
    public void setUp(Movable movable) {
        if(movable==null){
            return;
        }
        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::randomMove),
                new Wait<>(3)
        )).scheduleFor(movable);
    }
}
