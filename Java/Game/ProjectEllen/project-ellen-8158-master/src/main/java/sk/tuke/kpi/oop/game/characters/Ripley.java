package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    private int speed;
//    private int energy;
    private Disposable disposable;
    //private int ammo;
    private Animation RipleyAnimation;
    private Backpack backpack;
    private Firearm gun;
    private Health health;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("player died", Ripley.class);
    public Ripley() {
        super("Ellen");
        this.speed = 2;
        int ammo=0;
        RipleyAnimation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(RipleyAnimation);
        backpack=new Backpack("Ripley's backpack",10);
        stoppedMoving();
        health=new Health(100);
        health.onFatigued(() -> {
            setAnimation(new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE));
            getScene().getMessageBus().publish(RIPLEY_DIED, this);
        });
        gun = new Gun(ammo,150);

    }


    @Override
    public int getSpeed() {
        return this.speed;
    }
    public void setSpeed(int speed){
        this.speed=speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        RipleyAnimation.setRotation(direction.getAngle());
        RipleyAnimation.play();
    }

    @Override
    public void stoppedMoving() {
        RipleyAnimation.stop();
    }

    @Override
    public Backpack getBackpack() {
        return backpack;
    }
    public void showRipleyState(Scene scene) {
        Ripley ripley = scene.getFirstActorByType(Ripley.class);
        if (ripley != null) {
            int windowHeight = scene.getGame().getWindowSetup().getHeight();
            int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;

            scene.getGame().getOverlay().drawText("| Energy: " + ripley.getHealth().getValue(), 100, yTextPos);
            scene.getGame().getOverlay().drawText("| Ammo: " + ripley.getFirearm().getAmmo(), 260, yTextPos);
        }
    }
    public void decrease(){
        health.drain(1);
    }
    public Disposable stopDied() {
        return disposable;
    }

    public void energyDecrease() {
        if (health.getValue() > 0) {
            disposable = new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(this::decrease),
                    new Wait<>(0.5f)
                )
            ).scheduleFor(this);
        }
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public Firearm getFirearm() {
        return gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        gun=weapon;
    }
}
