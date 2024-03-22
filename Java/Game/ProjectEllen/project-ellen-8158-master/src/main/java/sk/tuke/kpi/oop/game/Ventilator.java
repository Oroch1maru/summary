package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

public class Ventilator extends AbstractActor implements Repairable {
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);
    private boolean checkOn;
    private Animation anim=new Animation("sprites/ventilator.png",32,32,0.1f);
    public Ventilator(){
        setAnimation(anim);
        getAnimation().stop();
        checkOn=false;
    }

    @Override
    public boolean repair() {
        if(checkOn==false){
            checkOn=true;
            getAnimation().play();
            getScene().getMessageBus().publish(VENTILATOR_REPAIRED,this);
            return true;
        }
        return false;
    }
}
