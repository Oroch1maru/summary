package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.Ripley;


public class Follow implements Behaviour<Movable> {

    private Disposable disposable = null;

    private Move<Movable> move = null;

    public void follow(Movable actor) {
        int ActorX = actor.getPosX();
        int ActorY = actor.getPosY();
        int RipleyX = actor.getScene().getFirstActorByType(Ripley.class).getPosX();
        int RipleyY = actor.getScene().getFirstActorByType(Ripley.class).getPosY();
        int newX=0;
        int newY=0;
        if(ActorX<RipleyX){
            newX=1;
        }
        else if(ActorX>RipleyY){
            newX=-1;
        }
        if(ActorY<RipleyY){
            newY=1;
        }
        else if(ActorY>RipleyY){
            newY=-1;
        }
        Direction direct = null;
        for (Direction value : Direction.values()) {
            if (newX == value.getDx() && newY == value.getDy()) {
                direct = value;
            }
        }
        if (move != null) {
            move.stop();
            disposable.dispose();
            move = null;
        }
        if (direct != null) {
            move = new Move<>(direct, 3);
            disposable = move.scheduleFor(actor);
        }

    }

    @Override
    public void setUp (Movable actor){
        if (actor != null) {
            new Loop<>(new ActionSequence<Movable>(
                new Invoke<>(() -> {
                    follow(actor);
                }),
                new Wait<>(1.5f)
            )).scheduleFor(actor);
        }
    }

}

