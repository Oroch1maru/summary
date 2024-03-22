package sk.tuke.kpi.oop.game.securityroom;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;
import sk.tuke.kpi.oop.game.characters.Alive;

public class Electricity extends SecurityItem {

    public Electricity(){
        super(new Animation("sprites/electricity.png", 16, 48, 0.25f,Animation.PlayMode.LOOP));
        getAnimation().setRotation(90);
    }



    @Override
    public void attack(){

        if(checkOn==true) {
            for (Actor alive : getScene().getActors()) {
                if (alive instanceof Alive && this.intersects(alive)) {
                    ((Alive) alive).getHealth().drain(5);
                    new ActionSequence<>(
                        new Invoke<>(() -> {
                            System.out.println(alive.getAnimation());
                            ((Alive) alive).getAnimation().setTint(Color.CORAL);
                            ((Alive) alive).setSpeed(1);
                        }),
                        new Wait<>(3),
                        new Invoke<>(() -> {
                            ((Alive) alive).setSpeed(2);
                            ((Alive) alive).getAnimation().setTint(Color.WHITE);
                        })
                    ).scheduleFor(this);
                }
            }
        }
    }

    @Override
    public void turnOff(){
        getAnimation().resetToFirstFrame();
        getAnimation().stop();
        checkOn=false;
    }

}
