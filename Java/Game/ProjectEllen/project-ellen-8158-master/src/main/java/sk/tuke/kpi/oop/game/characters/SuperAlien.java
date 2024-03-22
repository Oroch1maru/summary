package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Generator;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.behaviours.Follow;

public class SuperAlien extends Alien {

    public SuperAlien(int healthValue,Behaviour<? super Alien> behaviour) {
        super(healthValue, behaviour);
        setAnimation(new Animation("sprites/monster_2.png", 78, 127, 0.2f));
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        scene.getMessageBus().subscribe(Generator.Electricity_Off, (SuperAlien) -> this.setBehaviour(new Follow()));
    }

    public void setBehaviour(Behaviour<? super Alien> behaviour){
        this.behaviour=behaviour;
        this.setDamage(15);
        this.setSpeed(10);
    }

}

