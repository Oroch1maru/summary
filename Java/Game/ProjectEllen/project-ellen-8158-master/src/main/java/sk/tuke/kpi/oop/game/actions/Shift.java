package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Shift <A extends Keeper> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        if (getActor()!=null && !isDone() && getActor().getBackpack().peek()!=null) {
            getActor().getBackpack().shift();
            System.out.println("Shift is done");
        }
        setDone(true);
    }

}
