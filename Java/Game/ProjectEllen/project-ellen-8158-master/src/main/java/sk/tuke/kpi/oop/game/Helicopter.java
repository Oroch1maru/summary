package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {
    private int posX;
    private int posY;
    private boolean start=true;
    public Helicopter(){

        setAnimation(
            new Animation("sprites/heli.png",64, 64, 0.075f)
        );
    }

    private void search(){
        Player player=getScene().getLastActorByType(Player.class);
        if(player!=null) {
            if (start) {
                posX = this.getPosX();
                posY = this.getPosY();
                start = false;
            }
            int posx = player.getPosX();
            int posy = player.getPosY();
            if (posX > posx) {
                posX = posX - 1;
            } else if (posX < posx) {
                posX = posX + 1;
            }
            if (posY < posy) {
                posY = posY + 1;
            } else if (posY > posy) {
                posY = posY - 1;
            }
            this.setPosition(posX, posY);
            if (posY == posy && posX == posx) {
                player.setEnergy((player.getEnergy()) - 1);
            }
        }
    }
    public void searchAndDestroy(){
        new Loop<>(new Invoke<>(this::search)).scheduleFor(this);
    }

}
