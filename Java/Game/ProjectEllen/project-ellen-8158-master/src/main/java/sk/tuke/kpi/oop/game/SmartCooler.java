package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.graphics.Animation;
public class SmartCooler extends Cooler {
    private Reactor reactor;
    public SmartCooler(Reactor reactor) {
        super(reactor);
        this.reactor=reactor;
        setAnimation(
            new Animation("sprites/fan.png", 32, 32, 0.0f)
        );
    }


    @Override
    public void coolReactor() {
        if(this.reactor!=null) {
            int currentTemperature = reactor.getTemperature();
            if (currentTemperature >= 2500) {
                turnOn();
            } else if (currentTemperature < 1500) {
                turnOff();
            }
            if (this.isOn()) {
                this.reactor.decreaseTemperature(1);
            }
        }
    }
}
