package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;

import sk.tuke.kpi.oop.game.*;

import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;

import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.securityroom.Electricity;
import sk.tuke.kpi.oop.game.securityroom.Laser;


public class EscapeRoom implements SceneListener {
    private Ripley ripley;
    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if (name != null) {
                if ("ellen".equals(name)) {
                    return new Ripley();
                } else if ("energy".equals(name)) {
                    return new Energy();
                } else if ("ammo".equals(name)) {
                    return new Ammo();
                }
                else if ("alien".equals(name)) {
//                    return new Alien(100, new RandomlyMoving());
                    return new Alien();
                }
                else if ("front door".equals(name)) {
                    return new Door();
                }
            }
            return null;
        }
    }

//    @Override
//    public void sceneCreated(@NotNull Scene scene) {
//        World.ACTOR_ADDED_TOPIC<Alien>;
//    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
//        Laser laser=new Laser();
        scene.addActor(new Electricity(), 30, 248);

        scene.addActor(new Laser(), 70, 248);
        ripley= scene.getFirstActorByType(Ripley.class);
        scene.follow(ripley);
        Disposable movableCon = scene.getInput().registerListener(new MovableController(ripley));
        Disposable keeperCon = scene.getInput().registerListener(new KeeperController(ripley));
        Disposable shooterCon = scene.getInput().registerListener(new ShooterController(ripley));

        AlienEgg alienegg=new AlienEgg();
        scene.addActor(alienegg, 30, 400);
        scene.addActor(new Generator(),30,100);

    }

    @Override
    public void sceneUpdating(Scene scene) {
        ripley.showRipleyState(scene);
    }
}
