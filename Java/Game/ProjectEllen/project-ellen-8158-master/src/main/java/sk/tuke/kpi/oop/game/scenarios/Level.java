package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;

import sk.tuke.kpi.oop.game.*;

import sk.tuke.kpi.oop.game.behaviours.Follow;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.characters.SuperAlien;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;

import sk.tuke.kpi.oop.game.end.BadEnd;
import sk.tuke.kpi.oop.game.end.End;
import sk.tuke.kpi.oop.game.end.GoodEnd;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;
import sk.tuke.kpi.oop.game.securityroom.Electricity;
import sk.tuke.kpi.oop.game.securityroom.Laser;


public class Level implements SceneListener {
    private Ripley ripley;

    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if (name != null) {
                if ("ellen".equals(name)) {
                    return new Ripley();
                } else if ("Health".equals(name)) {
                    return new Energy();
                } else if ("Ammo".equals(name)) {
                    return new Ammo();
                }
                else if ("Alien".equals(name)) {
                    return new Alien(100, new Follow());
                }
                else if ("Door".equals(name)) {
                    return new Door("Start", Door.Orientation.HORIZONTAL);
                }
                else if ("LockedDoor".equals(name)) {
                    return new LockedDoor();
                }
                else if ("Mine".equals(name)) {
                    return new Mine();
                }
                else if ("Egg".equals(name)) {
                    return new AlienEgg();
                }
                else if ("Laser".equals(name)) {
                    return new Laser();
                }
                else if ("Electr".equals(name)) {
                    return new Electricity();
                }
                else if ("Generator".equals(name)) {
                    return new Generator();
                }
                else if ("key".equals(name)) {
                    return new AccessCard();
                }
                else if ("SuperAlien".equals(name)) {
                    return new SuperAlien(350,null);
                }
            }
            return null;
        }
    }


    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        SceneListener.super.sceneInitialized(scene);
        ripley= scene.getFirstActorByType(Ripley.class);
        scene.follow(ripley);
        Disposable movableCon = scene.getInput().registerListener(new MovableController(ripley));
        Disposable keeperCon = scene.getInput().registerListener(new KeeperController(ripley));
        Disposable shooterCon = scene.getInput().registerListener(new ShooterController(ripley));
        scene.getGame().pushActorContainer(ripley.getBackpack());

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> scene.addActor(new BadEnd(),1000,1000));
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keeperCon.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> shooterCon.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movableCon.dispose());
        scene.getMessageBus().subscribe(LockedDoor.Door_Open, (Ripley) -> scene.addActor(new GoodEnd(),1000,1000));
    }

    @Override
    public void sceneUpdating(Scene scene) {
        ripley.showRipleyState(scene);
    }
}
