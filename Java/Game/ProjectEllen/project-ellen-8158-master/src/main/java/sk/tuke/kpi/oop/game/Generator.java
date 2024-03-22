package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Health;
import sk.tuke.kpi.oop.game.securityroom.Security;

import java.util.List;

public class Generator extends AbstractActor implements Switchable, Alive {
    private boolean checkOn;
    private List<Actor> devices;
    private Health health;
    public static final Topic<Generator> Electricity_On = Topic.create("Electricity On", Generator.class);
    public static final Topic<Generator> Electricity_Off = Topic.create("Electricity Off", Generator.class);
    public Generator(){
        setAnimation(
            new Animation("sprites/generator.png",32,48)
        );
        health=new Health(50);
        health.onFatigued(() -> {
            turnOff();
        });
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        turnOn();
    }

    @Override
    public void turnOn() {
        devices = this.getScene().getActors();
        if(checkOn==false){
            checkOn=true;
            getScene().getMessageBus().publish(Electricity_On,this);
        }
        if(this.devices!=null) {
            for (Actor dev : devices) {
                if(dev instanceof Security){
                    ((Security) dev).setPowered(true);
                }
            }
        }
    }



    @Override
    public void turnOff() {
        new ActionSequence<>(
            new Invoke<>(()->setAnimation(new Animation("sprites/large_explosion.png",32,32,0.2f, Animation.PlayMode.ONCE))),
            new Wait<>(1.6f),
            new Invoke<>(()->this.getScene().removeActor(this))
        ).scheduleFor(this);
        if(checkOn==true){
            checkOn=false;
            getScene().getMessageBus().publish(Electricity_Off,this);
        }
        if(this.devices!=null) {
            for (Actor dev : devices) {
                if(dev instanceof Security){
                    System.out.println(dev);
                    ((Security) dev).setPowered(false);
                }

            }
        }
    }


    @Override
    public boolean isOn() {
        return checkOn;
    }

    @Override
    public Health getHealth() {
        return health;
    }
}
