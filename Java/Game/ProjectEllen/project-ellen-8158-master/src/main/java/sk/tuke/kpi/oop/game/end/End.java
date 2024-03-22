package sk.tuke.kpi.oop.game.end;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class End extends AbstractActor {
    public End(Animation lazerOn){
        setAnimation(lazerOn);
        System.out.println("Hello from End");

    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        int windowHeight = this.getScene().getGame().getWindowSetup().getHeight();
        int windowWidth = this.getScene().getGame().getWindowSetup().getWidth();
        this.getScene().follow(this);
        int yTextPos= windowHeight/2;
        int xTextPos = windowWidth/2+500;
        this.setPosition(xTextPos,yTextPos);
    }


}
