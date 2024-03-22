package sk.tuke.kpi.oop.game.controllers;


import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Movable actor;
    private Set<Input.Key> keys;
    private Move<Movable> move = null;
    private Disposable dispos;
    public MovableController(Movable actor){
        this.actor=actor;
        this.keys = new HashSet<>();
    }
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );

    private void stopMove(){
        if(move!=null){
            move.stop();
            dispos.dispose();
            move=null;
        }
    }
    private void updateMove() {
        Direction direct =null;
        for (Input.Key kluc:keys) {
            if(direct!=null)direct = direct.combine(keyDirectionMap.get(kluc));
            else{direct=keyDirectionMap.get(kluc);}
        }
        stopMove();

        if (direct!=null) {
            move = new Move<>(direct, 10);
            dispos = move.scheduleFor(actor);
        }
    }


    @Override
    public void keyPressed(Input.Key key) {
        if (keyDirectionMap.containsKey(key)) {
            keys.add(key);
            updateMove();
        }
    }

    @Override
    public void keyReleased(Input.Key key) {
        if (keyDirectionMap.containsKey(key)) {
            keys.remove(key);
            updateMove();
        }
    }
}
