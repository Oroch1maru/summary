package sk.tuke.kpi.oop.game.controllers;


import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.characters.Armed;

public class ShooterController implements KeyboardListener {
    private Armed shooter;
    public ShooterController(Armed shooter){
        this.shooter=shooter;
    }

    @Override
    public void keyPressed(Input.Key key) {
        if(shooter!=null && key!=null && key== Input.Key.SPACE){
            Fire<Armed> fire= new Fire<>();
            //System.out.println(fire.getActor());
            fire.setActor(shooter);
            fire.scheduleFor(shooter);
        }
    }
}

