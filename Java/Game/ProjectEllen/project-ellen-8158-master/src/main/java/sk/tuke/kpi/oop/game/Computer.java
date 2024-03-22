package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer{
    private boolean energy_on=false;

    public Computer() {
        setAnimation(
            new Animation("sprites/computer.png", 80, 48, 0.0f)
        );
    }
    public void setPowered(boolean energy){
        this.energy_on=energy;
        if(this.energy_on==true){
            setAnimation(
                new Animation("sprites/computer.png", 80, 48, 0.2f)
            );
        }
        else{
            setAnimation(
                new Animation("sprites/computer.png", 80, 48, 0.0f)
            );
        }
    }

    public int add(int one, int two){
        if(this.energy_on==true){
            return one+two;
        }
        return 0;

    }

    public float add(float one, float two){
        if(this.energy_on==true){
            return one+two;
        }
        return 0;
    }
    public int sub(int one, int two){
        if(this.energy_on==true){
            return one-two;
        }
        return 0;

    }
    public float sub(float one, float two){
        if(this.energy_on==true){
            return one-two;
        }
        return 0;
    }
}
