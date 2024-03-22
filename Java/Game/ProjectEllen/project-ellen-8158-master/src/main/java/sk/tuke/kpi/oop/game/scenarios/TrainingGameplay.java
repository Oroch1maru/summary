package sk.tuke.kpi.oop.game.scenarios;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.oop.game.Cooler;
import sk.tuke.kpi.oop.game.Reactor;

public class TrainingGameplay extends Scenario {
    @Override
    public void setupPlay(Scene scene){
        Reactor reactor = new Reactor();
        Cooler smartcooler = new Cooler(reactor);
        Cooler smartcooler_two = new Cooler(reactor);
        scene.addActor(reactor, 145, 95);
        scene.addActor(smartcooler, 240, 95);
        scene.addActor(smartcooler_two, 240, 155);
        reactor.turnOn();
    }
}
