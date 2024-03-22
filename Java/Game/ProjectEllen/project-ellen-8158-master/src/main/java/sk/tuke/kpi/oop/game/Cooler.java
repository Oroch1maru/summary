package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
public class Cooler extends AbstractActor implements Switchable{

    private Reactor reactor;
    private boolean check_on=false;
    public Cooler(Reactor reactor) {
        setAnimation(
            new Animation("sprites/fan.png", 32, 32, 0.0f, Animation.PlayMode.LOOP_PINGPONG)
        );
        this.reactor = reactor;
    }


    public void turnOff(){
        if(this.check_on==true){
            this.check_on=false;
            setAnimation(
                new Animation("sprites/fan.png", 32, 32, 0.0f)
            );
        }
    }
    public void turnOn(){
        if(this.check_on==false){
            this.check_on=true;
            setAnimation(
                new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG)
            );
        }
    }

    public boolean isOn(){
        return this.check_on;
    }

    public void coolReactor(){
        if(reactor==null){
            return;
        }
        else if(this.check_on==true){
            this.reactor.decreaseTemperature(1);
        }
    }
////
    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
