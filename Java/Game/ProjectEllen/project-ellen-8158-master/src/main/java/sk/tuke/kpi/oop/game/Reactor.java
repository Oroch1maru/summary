package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.gamelib.Scene;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable,Repairable{
    private int temperature;
    private Set<EnergyConsumer> devices;
    private int damage=0;
    private int new_damage=0;
    private int old_damage=0;
    private boolean check_on=false;
    private Animation norma_animation;
    private Animation hot_animation;
    private Animation very_hot_animation;
    public Reactor() {
        this.devices = new HashSet<>();
        setAnimation(
            new Animation("sprites/reactor.png")
        );
        this.temperature=0;
        this.damage=0;
        this.norma_animation=new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.hot_animation=new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        this.very_hot_animation=new Animation("sprites/reactor_hot.png", 80, 80, 0.05f/2f, Animation.PlayMode.LOOP_PINGPONG);
    }
    public int getDamage(){
        return this.damage;
    }
    private void updateAnimation(){
        if(this.damage>=100 || this.temperature>=6000){
            setAnimation(
                new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG)
            );
        }
        else if(this.temperature<=4000){
            setAnimation(norma_animation);
        }
        else if(this.temperature>4000) {
            if(this.damage<=75) {
                setAnimation(hot_animation);
            }
            else{
                setAnimation(very_hot_animation);
            }
        }
    }

    //temperature
    public int getTemperature(){
        return this.temperature;
    }

    private void checkCritical(){
        if(this.damage>=100){
            this.damage=100;
            if(this.devices!=null) {
                for (EnergyConsumer dev : devices) {
                    dev.setPowered(false);
                }
            }
            this.check_on=false;
        }
    }
    private void changeTemperature(int increment){
        if(this.damage>=33 && this.damage<=66){
            this.temperature = this.temperature + Math.round(increment*1.5f);
        }
        else if(this.damage>66){
            this.temperature = this.temperature + increment*2;
        }
        else {
            this.temperature = this.temperature + increment;
        }
    }
    private void changeDamage(){
        if(new_damage==this.damage) {
            new_damage = (this.temperature - 2000) / 40;
            this.damage = new_damage;
        }
        else if(old_damage==0 && new_damage!=this.damage){
            new_damage=this.damage;
        }
        if(old_damage!=0){
            new_damage = (this.temperature - 2000) / 40;
            if(new_damage<=old_damage) {
                this.damage = old_damage;
            }
            else{
                old_damage=0;
            }
        }
    }
    public void increaseTemperature(int increment){
        if(increment>=0 && this.check_on==true) {
            changeTemperature(increment);
            if (this.temperature >= 2000 && this.damage < 100) {
                changeDamage();
                checkCritical();
            }
            updateAnimation();
        }

    }
    public void decreaseTemperature(int decrement ) {
        if(decrement>=0 && this.check_on==true && this.temperature!=0) {
            old_damage=this.damage;
            if (this.damage >= 50) {
                if(decrement / 2==0){
                    this.temperature = this.temperature - 1;
                }
                else{
                    this.temperature = this.temperature - (decrement / 2);
                }

            } else if (this.damage != 100) {
                this.temperature = this.temperature - decrement;
            }
            if(this.temperature<=0){
                this.temperature=0;
            }
            updateAnimation();
        }
    }

    //tools
    public boolean repair(){
        if(this.damage>0 && this.damage<100){
            this.temperature=((this.damage-50)*40)+2000;
            if(this.damage>50){
                this.damage=this.damage-50;
            }
            else{
                this.damage=0;
            }
            return true;
        }
        else{
            return false;
        }
    }
    public boolean extinguish(){
        if(this.temperature>=4000 && this.damage==100){
            this.temperature=4000;
            setAnimation(
                new Animation("sprites/reactor_extinguished.png")
            );
            return true;
        }
        else{
            return false;
        }
    }

    //power reactor
    public void turnOn(){
        if(this.damage==100){
            this.check_on=false;
        }
        else {
            if(this.devices!=null) {
                for (EnergyConsumer dev : devices) {
                    dev.setPowered(true);
                }
            }
            this.check_on = true;
            updateAnimation();
        }
    }
    public void turnOff(){
        if(this.damage<100){
            this.check_on=false;
            setAnimation(
                new Animation("sprites/reactor.png")
            );
            if(this.devices!=null) {
                for (EnergyConsumer dev : devices) {
                    dev.setPowered(false);
                }
            }
        }
    }
    public boolean isOn(){
        return this.check_on;
    }

    //device
    public void addDevice(EnergyConsumer device) {
        this.devices.add(device);
        if(this.check_on==true) {
            for (EnergyConsumer dev : devices) {
                dev.setPowered(true);
            }
        }
    }
    public void removeDevice(EnergyConsumer device){
        if(this.devices!=null){

//            for (EnergyConsumer dev : devices) {
//                if (dev==device) {
//                    devices.remove(dev);
//                    dev.setPowered(false);
//                }
//            }
            devices.remove(device);
            device.setPowered(false);
        }
    }

    //increase temperature every second
    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }
}
