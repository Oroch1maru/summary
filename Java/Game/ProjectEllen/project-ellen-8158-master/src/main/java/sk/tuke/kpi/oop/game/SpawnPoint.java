package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.awt.geom.Ellipse2D;


public class SpawnPoint extends AbstractActor {
    private int countAlien;
    private Ellipse2D.Float ellipse;
    private Ellipse2D.Float secondEllipse;
    private Disposable dispos;
    private boolean checkOn=false;
    private Disposable disposSpawn;
    private Ripley foundRipley;

    public SpawnPoint(int countAlien) {
        this.countAlien=countAlien;
        setAnimation(
            new Animation("sprites/spawn.png",32,32)
        );
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        ellipse = new Ellipse2D.Float(this.getPosX()-34,this.getPosY()-34,102,102);
        foundRipley=scene.getFirstActorByType(Ripley.class);

        dispos = new Loop<>(
            new Invoke<>(this::check)
        ).scheduleFor(this);

        disposSpawn=new Loop<>(
                new When<>(
                    () -> checkOn == true,
                    new ActionSequence<>(
                        new Invoke<>(this::spawn),
                        new Wait<>(3.0f)
                    )
                )
        ).scheduleFor(this);
    }
    public void spawn(){
        if(countAlien>0){
            secondEllipse = new Ellipse2D.Float(foundRipley.getPosX(),foundRipley.getPosY(),32,32);
            if(checkOn==true && ellipse.intersects(secondEllipse.getBounds2D())) {
                this.getScene().addActor(new Alien(), this.getPosX(), this.getPosY());
                System.out.println(countAlien);
                countAlien--;

           }
        }
        else{
            if(countAlien<=0){
                System.out.println("Spawn is Done\n");
                disposSpawn.dispose();
            }

        }
    }


    public void check(){
        secondEllipse = new Ellipse2D.Float(foundRipley.getPosX(),foundRipley.getPosY(),32,32);
        if(ellipse.intersects(secondEllipse.getBounds2D())){
            checkOn=true;
            dispos.dispose();
        }
    }

}
