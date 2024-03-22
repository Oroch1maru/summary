package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;

public class Energy extends AbstractActor implements Usable<Alive>{
    public Energy(){
        Animation energyAnimation = new Animation("sprites/energy.png", 16, 16);
        setAnimation(energyAnimation);
    }

    @Override
    public void useWith(Alive alive) {
        if(alive!=null && alive.getHealth().getValue()!=100){
            alive.getHealth().restore();
            getScene().removeActor(this);
        }
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }
}
