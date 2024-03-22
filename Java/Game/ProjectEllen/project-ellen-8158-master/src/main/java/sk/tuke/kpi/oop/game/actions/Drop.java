package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop <A extends Keeper> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        if (getActor() == null || getActor().getScene() == null || isDone()) {
            setDone(true);
            return;
        }
        Scene scene = getActor().getScene();
        Collectible itemInBackpack=getActor().getBackpack().peek();
        if(itemInBackpack!=null){
            getActor().getBackpack().remove(itemInBackpack);
            int posX=getActor().getPosX()+ getActor().getWidth()/2-itemInBackpack.getWidth()/2;
            int posY=getActor().getPosY()+ getActor().getHeight()/2-itemInBackpack.getHeight()/2;
            scene.addActor(itemInBackpack,posX , posY);
        }
        else{
            scene.getOverlay().drawText((getActor().getBackpack().getName()+" is empty"),0, 0).showFor(2);
        }
        setDone(true);
    }
}
