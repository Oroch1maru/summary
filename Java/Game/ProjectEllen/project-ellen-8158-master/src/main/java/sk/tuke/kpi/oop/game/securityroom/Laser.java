package sk.tuke.kpi.oop.game.securityroom;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Laser extends SecurityItem {

    public Laser() {
        super(new Animation("sprites/laser.png", 16, 48, 0.25f, Animation.PlayMode.LOOP));
    }
}
