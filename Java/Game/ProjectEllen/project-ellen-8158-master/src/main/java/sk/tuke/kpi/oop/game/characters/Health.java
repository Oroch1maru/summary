package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private int startHealth;
    private boolean checkOn;
    private List<FatigueEffect> efects;
    private int maxHealth;
    public Health(int startHealth, int maxHealth){
        this.maxHealth=maxHealth;
        this.startHealth=startHealth;
        efects = new ArrayList<>();
        checkOn=false;
    }
    public Health(int hp){
        this.maxHealth=hp;
        this.startHealth=hp;
        efects = new ArrayList<>();
    }
    public void refill(int amount){
        startHealth=startHealth+amount;
        if(startHealth>=maxHealth){
            startHealth=maxHealth;
        }
    }
    public void restore(){
        startHealth=maxHealth;
    }

    public void drain(int amount){
        startHealth=startHealth-amount;
        if(startHealth<=0){
            startHealth=0;
            if(efects!=null && checkOn==false) {
                for (Health.FatigueEffect effect : efects) {
                    effect.apply();
                    checkOn=true;
                }
            }
        }
    }

    public void exhaust(){
        startHealth=0;
        if(efects!=null && checkOn==false) {
            for (Health.FatigueEffect effect : efects) {
                effect.apply();
                checkOn=true;
            }
        }
    }

    public int getValue(){
        return startHealth;
    }

    public void onFatigued(FatigueEffect effect){
        if(effect!=null){
            efects.add(effect);
        }
    }

    @FunctionalInterface
    public interface FatigueEffect {
        void apply();
    }
}
