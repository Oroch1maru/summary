package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Bullet extends AbstractActor implements Fireable{
    private Animation bulletAnim;
    private int speed;
    public Bullet(){
        bulletAnim=new Animation("sprites/bullet.png", 16, 16);
        setAnimation(bulletAnim);
        speed=4;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        if(direction!=null && direction!=Direction.NONE){
            bulletAnim=this.getAnimation();
            bulletAnim.setRotation(direction.getAngle());
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new Invoke<>(() -> {
                for (Actor enemy : getScene().getActors()) {
                    if ((enemy instanceof Alive) && !(enemy instanceof Ripley) && this.intersects(enemy)) {
                        ((Alive) enemy).getHealth().drain(15);
                        this.getScene().removeActor(this);
                    }
                }
            })
        ).scheduleFor(this);
    }

    @Override
    public void collidedWithWall() {
        if(this!=null){
            this.getScene().removeActor(this);
        }
    }
}
