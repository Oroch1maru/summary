package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements EnergyConsumer,Switchable{
    private boolean lightOn=false;
    private boolean electricOn=false;
    public Light() {
        setAnimation(
            new Animation("sprites/light_off.png")
        );
    }

    public boolean isElectricOn(){return electricOn;}




    public void toggle() {
        if(lightOn==true){
            lightOn=false;
            setAnimation(
                new Animation("sprites/light_off.png")
            );
        }
        else if(lightOn==false){
            lightOn=true;
            if(this.electricOn==true){
                setAnimation(
                    new Animation("sprites/light_on.png")
                );
            }

        }
    }

    public void turnOn(){
        lightOn=true;
        if(this.electricOn==true){
            setAnimation(
                new Animation("sprites/light_on.png")
            );
        }
    }

    public void turnOff(){
        lightOn=false;
        setAnimation(
            new Animation("sprites/light_off.png")
        );

    }
    public void setPowered(boolean powered) {
        this.electricOn = powered;
        if(this.electricOn==false){
            turnOff();
        }
        else if(lightOn==true){
            setAnimation(
                new Animation("sprites/light_on.png")
            );
        }
    }

    public boolean isOn(){
        return this.lightOn;
    }

}
