package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import java.util.Random;

public class DefectiveLight extends Light implements Repairable {
    private boolean check_on=false;
    private Disposable dispos;
    public DefectiveLight() {
        setAnimation(
            new Animation("sprites/light_off.png")
        );
    }

    private void lightorno(){
        if(isElectricOn()==true) {
            Random random = new Random();
            int randomNumber = random.nextInt(20);
            if (randomNumber == 1) {
                toggle();
            }
            check_on=false;
        }

    }
    public boolean repair(){
        if(isElectricOn()==true && check_on!=true) {
            dispos.dispose();
            setAnimation(
                new Animation("sprites/light_on.png")
            );
            check_on=true;
            new ActionSequence<>(
                new Wait<>(10),
                new Loop<>(new Invoke<Actor>(this::lightorno))
            ).scheduleFor(this);
            return true;
        }
        return false;
    }
    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        this.dispos = new Loop<>(new Invoke<Actor>(this::lightorno)).scheduleFor(this);
    }

    public void defectiveOn(){
        this.dispos = new Loop<>(new Invoke<Actor>(this::lightorno)).scheduleFor(this);
    }
}
