package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;


public class Fire <A extends Armed> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        Move<Movable> move = null;
        if (isDone()) {
            return;
        }
        if (getActor() != null) {
            Fireable fireable = getActor().getFirearm().fire();
            if (fireable != null) {
                Direction direct = Direction.fromAngle(getActor().getAnimation().getRotation());
                int dirX = direct.getDx();
                int dirY = direct.getDy();
                getActor().getScene().addActor(fireable, getActor().getPosX() + 8 + dirX * 16, getActor().getPosY() + 8 + dirY * 16);
                fireable.startedMoving(direct);
                move = new Move<>(direct, 10);
                move.scheduleFor(fireable);
            }
        }
        setDone(true);
    }
}
