package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;

public class Door extends AbstractActor implements Openable, Usable<Actor> {
    protected boolean checkOn;
    private Scene scene;
    protected Animation animDoorOn;
    public enum Orientation { VERTICAL, HORIZONTAL };
    private Animation animDoorOff;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    private String sprites="sprites/vdoor.png";
    private Orientation orientation;
    public Door(){
        animDoorOff=new Animation(sprites, 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
        animDoorOn=new Animation(sprites, 16, 32, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(animDoorOn);
        getAnimation().stop();
        this.orientation=Orientation.VERTICAL;
        checkOn=false;
    }
    public Door(String name, Orientation orientation){
        super(name);
        if(orientation==Orientation.VERTICAL){
            this.orientation=Orientation.VERTICAL;
            animDoorOff=new Animation(sprites, 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            animDoorOn=new Animation(sprites, 16, 32, 0.1f, Animation.PlayMode.ONCE);
        }
        else if(orientation==Orientation.HORIZONTAL){
            this.orientation=Orientation.HORIZONTAL;
            animDoorOff=new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);
            animDoorOn=new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE);

        }
        setAnimation(animDoorOn);
        getAnimation().stop();
        checkOn=false;
    }
    @Override
    public void useWith(Actor actor) {
        if (isOpen())
            close();
        else
            open();
    }



    public void open() {
        if(checkOn==false) {
            if(orientation==Orientation.VERTICAL) {
                this.scene.getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
                this.scene.getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
            }
            else if(orientation==Orientation.HORIZONTAL){
                this.scene.getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
                this.scene.getMap().getTile((this.getPosX() / 16)+1, this.getPosY() / 16).setType(MapTile.Type.CLEAR);
            }
            checkOn = true;
            setAnimation(animDoorOn);
            getAnimation().play();
            getAnimation().resetToFirstFrame();
            getScene().getMessageBus().publish(DOOR_OPENED,this);
        }
    }

    @Override
    public void close() {
        if(checkOn==true) {
            if(orientation==Orientation.VERTICAL) {
                this.scene.getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
                this.scene.getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
            }
            else if(orientation==Orientation.HORIZONTAL){
                this.scene.getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
                this.scene.getMap().getTile((this.getPosX() / 16)+1, this.getPosY() / 16).setType(MapTile.Type.WALL);
            }
            checkOn = false;
            setAnimation(animDoorOff);
            getAnimation().play();
            getAnimation().resetToFirstFrame();
            getScene().getMessageBus().publish(DOOR_CLOSED,this);
        }
    }
    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.scene=scene;
        //System.out.println(getScene().getMap().getTileWidth());
        this.scene.getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
        this.scene.getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.WALL);
        if(orientation==Orientation.HORIZONTAL){
            this.scene.getMap().getTile(this.getPosX() / 16, this.getPosY() / 16).setType(MapTile.Type.WALL);
            this.scene.getMap().getTile((this.getPosX() / 16)+1, this.getPosY() / 16).setType(MapTile.Type.WALL);
            this.scene.getMap().getTile(this.getPosX() / 16, (this.getPosY() / 16) + 1).setType(MapTile.Type.CLEAR);
        }
    }


    @Override
    public boolean isOpen() {
        return checkOn;
    }
}
