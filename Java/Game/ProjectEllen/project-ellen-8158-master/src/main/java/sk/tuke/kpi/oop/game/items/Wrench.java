package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench extends BreakableTool<DefectiveLight>  implements Collectible {
    public Wrench() {
        super(2);
        setAnimation(
            new Animation("sprites/wrench.png")
        );
    }

    @Override
    public void useWith(DefectiveLight defectiveLight){
        if(defectiveLight!=null && defectiveLight.repair()==true){
            super.useWith(defectiveLight);
        }
    }

    @Override
    public Class<DefectiveLight> getUsingActorClass() {
        return DefectiveLight.class;
    }

}
