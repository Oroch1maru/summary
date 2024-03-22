package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Generator;
import sk.tuke.kpi.oop.game.characters.SuperAlien;

public class LockedDoor extends Door{
    public static final Topic<LockedDoor> Door_Open = Topic.create("Is open", LockedDoor.class);
    private boolean checkLock;
    public LockedDoor(){
        super();
        checkLock=false;
    }
    public void lock(){
        if(checkLock==true){
            checkLock=false;
            super.close();
        }
    }

    public void unlock(){
        if(checkLock==false){
            checkLock = true;
            open();

        }
    }
    @Override
    public void open() {
        SuperAlien superAlien=this.getScene().getFirstActorByType(SuperAlien.class);
        if(checkOn==false && superAlien==null) {
            this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            this.getScene().getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
            checkOn = true;
            setAnimation(animDoorOn);
            getAnimation().play();
            getAnimation().resetToFirstFrame();

            getScene().getMessageBus().publish(Door_Open,this);
        }
    }

    public boolean isLocked(){
        return checkLock;
    }

    @Override
    public void useWith(Actor actor) {
        if (isLocked())
            super.useWith(actor);
    }


}
