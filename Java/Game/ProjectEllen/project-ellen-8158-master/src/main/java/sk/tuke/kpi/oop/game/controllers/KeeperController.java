package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
//import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
//import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Usable;


public class KeeperController implements KeyboardListener {
    private Keeper keeper;

    public KeeperController(Keeper keeper) {
        this.keeper = keeper;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if (key == Input.Key.ENTER) {
            new Take<>().scheduleFor(keeper);
        }
        if (key == Input.Key.BACKSPACE) {
            new Drop<>().scheduleFor(keeper);
        }
        if (key == Input.Key.S) {
            new Shift<>().scheduleFor(keeper);
        }

        if (key == Input.Key.U) {
            useAction();
        }

        if (key == Input.Key.B) {
            useItem();
        }

    }

    private void useAction() {
        Usable<?> actor = keeper.getScene().getActors().stream().filter(Usable.class::isInstance).filter(keeper::intersects).map(Usable.class::cast).findFirst().orElse(null);
        System.out.println(actor);
        if (actor != null) {
            new Use<>(actor).scheduleForIntersectingWith(keeper);
        }
    }

    private void useItem() {
        if (keeper.getBackpack().peek() instanceof Usable) {
            new Use<>((Usable<?>)keeper.getBackpack().peek()).scheduleForIntersectingWith(keeper);
        }
    }


}
