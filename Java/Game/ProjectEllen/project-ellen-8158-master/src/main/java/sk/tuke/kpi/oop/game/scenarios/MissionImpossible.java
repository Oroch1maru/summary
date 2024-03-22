package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;


public class MissionImpossible implements SceneListener {

    private Ripley ripley;
    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {

            if (name != null) {
                if ("ellen".equals(name)) {
                    return new Ripley();
                } else if ("energy".equals(name)) {
                    return new Energy();
                }
                else if("door".equals(name)){
                    return new LockedDoor();
                }
                else if("access card".equals(name)){
                    return new AccessCard();
                }
                else if("locker".equals(name)){
                    return new Locker();
                }
                else if("ventilator".equals(name)){
                    return new Ventilator();
                }
            }
            return null;
        }
    }


    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ripley= scene.getFirstActorByType(Ripley.class);
        scene.follow(ripley);
        Disposable movable=scene.getInput().registerListener(new MovableController(ripley));
        Disposable keeper=scene.getInput().registerListener(new KeeperController(ripley));
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, (Ripley) -> ripley.energyDecrease());
        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, (Ripley) -> ripley.stopDied().dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> movable.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, (Ripley) -> keeper.dispose());


        scene.getGame().pushActorContainer(ripley.getBackpack());
    }
    @Override
    public void sceneUpdating(Scene scene) {
        ripley.showRipleyState(scene);
    }
}
