package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;


public class Hammer extends BreakableTool<Repairable>  implements Collectible{
    public Hammer() {
        super(1);
        animation();
    }
    public Hammer(int uses) {
        super(uses);
        animation();
    }

    private void animation(){
        setAnimation(
            new Animation("sprites/hammer.png")
        );
    }

    @Override
    public void useWith(Repairable repairable){
        if(repairable!=null && repairable.repair()==true){
            super.useWith(repairable);
        }
    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }

}

























//public class GenericTest<T>{
//    private T value;
//    public GenericTest(T value){
//        this.value=value;
//    }
//    public T getValue(){
//        return value;
//    }


//}
