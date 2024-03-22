package sk.tuke.kpi.oop.game.scenarios;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.*;

public class FirstSteps implements SceneListener{
    private Ripley ripley;

    @Override
    public void sceneInitialized(Scene scene){

        ripley = new Ripley();
        scene.addActor(ripley, 0, 0);
        MovableController movableController = new MovableController(ripley);
        scene.getInput().registerListener(movableController);

        KeeperController keeperController = new KeeperController(ripley);
        scene.getInput().registerListener(keeperController);


        Energy energy = new Energy();
        scene.addActor(energy,-100, 50);

        Ammo ammo = new Ammo();
        scene.addActor(ammo,-200, 50);


        Hammer hammer= new Hammer();
        Wrench wrench=new Wrench();
        FireExtinguisher fireExtinguisher=new FireExtinguisher();
        Hammer hammer1= new Hammer();
        Wrench wrench1=new Wrench();
        FireExtinguisher fireExtinguisher1=new FireExtinguisher();
        Hammer hammer2= new Hammer();
        Wrench wrench2=new Wrench();
        FireExtinguisher fireExtinguisher2=new FireExtinguisher();
        Hammer hammer3= new Hammer();
        Wrench wrench3=new Wrench();
//        ripley.getBackpack().add(wrench);
//        ripley.getBackpack().add(hammer);
//        ripley.getBackpack().add(fireExtinguisher);
        scene.getGame().pushActorContainer(ripley.getBackpack());
//
//
//        System.out.println(ripley.getBackpack().getContent());
//        ripley.getBackpack().shift();
//        System.out.println(ripley.getBackpack().getContent());

        scene.addActor(hammer, 100, -50);
        scene.addActor(fireExtinguisher, 80, 50);
        scene.addActor(wrench, 23, -50);
        scene.addActor(hammer1, 100, -35);
        scene.addActor(fireExtinguisher1, 80, 50);
        scene.addActor(wrench1, 64, -50);
        scene.addActor(hammer2, -76, -50);
        scene.addActor(fireExtinguisher2, 80, -76);
        scene.addActor(wrench2, 1, -50);
        scene.addActor(hammer3, -76, -76);
        scene.addActor(wrench3, -13, 50);

    }

    @Override
    public void sceneUpdating(Scene scene) {
        ripley.showRipleyState(scene);
    }
}
