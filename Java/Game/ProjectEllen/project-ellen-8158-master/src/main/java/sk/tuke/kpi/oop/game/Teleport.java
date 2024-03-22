package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
//import sk.tuke.kpi.gamelib.Scene;
//import sk.tuke.kpi.gamelib.actions.Invoke;
//import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.graphics.Animation;
import java.util.List;

public class Teleport extends AbstractActor {
    private Teleport secondTeleport;
    private Player player;
    public Teleport(Teleport teleport){
        this.secondTeleport=teleport;
        setAnimation(
            new Animation("sprites/lift.png",48, 48)
        );
    }
    public void setDestination(Teleport destinationTeleport){
        if(this!=destinationTeleport) {
            this.secondTeleport = destinationTeleport;
        }
    }

    public Teleport getDestination(){
        return this.secondTeleport;
    }

    public void teleportPlayer(Player player){
        if(secondTeleport!=null) {
            this.player=player;
            //Teleport to the center:Done
            int posX = (secondTeleport.getPosX()+(secondTeleport.getWidth()/2));
            int posY = (secondTeleport.getPosY()+(secondTeleport.getHeight()/2));
            this.player.setPosition((posX-(this.player.getWidth()/2)), (posY-(this.player.getHeight()/2)));
            System.out.println("Player Width:"+this.player.getWidth());
            System.out.println("Player Height:"+this.player.getHeight());
            System.out.println("SecondTeleport X:"+posX);
            System.out.println("SecondTeleport Y:"+posY);
            System.out.println("SecondTeleport Width:"+secondTeleport.getWidth());
            System.out.println("SecondTeleport Height:"+secondTeleport.getHeight());
        }
    }

//    @Override
//    public void addedToScene(Scene scene) {
//        super.addedToScene(scene);
//        new Loop<>(new Invoke<>(this::checkTeleport)).scheduleFor(this);
//    }

    public void checkTeleport(){
        List<Actor> actors = getScene().getActors();
        for (Actor actor : actors) {
            if (actor.getName().equals("Player") && secondTeleport != null && actor!=null) {
                System.out.println("Player X:"+(actor.getPosX() ));
                System.out.println("Player Y:"+(actor.getPosY()));
//                    System.out.println("Player X:"+(this.getPosX() ));
//                    System.out.println("Player Y:"+(this.getPosX()));
//                    if ((actor.getPosX() ) == (this.getPosX()) && (actor.getPosY()) == (this.getPosY())) {
//                        System.out.println("Pobeda");
//                        teleportPlayer((Player) actor);
//                    }
            }
        }
    }
}
