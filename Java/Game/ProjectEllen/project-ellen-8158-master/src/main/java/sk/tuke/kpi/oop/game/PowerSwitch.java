package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor{
    private Switchable device;

    public PowerSwitch(Switchable device) {
        this.device = device;
        setAnimation(
            new Animation("sprites/switch.png")
        );
    }
    public Switchable getDevice() {
        return device;
    }
    public void switchOn() {
        if (this.device != null) {
            this.device.turnOn();
        }
        setAnimation(
            new Animation("sprites/switch.png")
        );
    }
    public void switchOff() {
        if (this.device != null) {
            this.device.turnOff();
            getAnimation().setTint(Color.GRAY);
        }
    }
}

