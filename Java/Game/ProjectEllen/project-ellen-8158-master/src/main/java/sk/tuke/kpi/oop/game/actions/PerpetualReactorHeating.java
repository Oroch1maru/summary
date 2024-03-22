package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {
    private int temparature;
    public PerpetualReactorHeating(int temparature){
        this.temparature=temparature;
    }
    public void execute(float deltaTime){
        Reactor reactor = getActor();
        if (reactor != null) {
            reactor.increaseTemperature(temparature);
        }
    }
}
