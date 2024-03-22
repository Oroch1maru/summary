package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int startAmmo;
    private int maxAmmo;
    public Firearm(int startAmmo, int maxAmmo){
        this.maxAmmo=maxAmmo;
        this.startAmmo=startAmmo;
    }
    public Firearm(int hp){
        this.maxAmmo=hp;
        this.startAmmo=hp;
    }

    public int getAmmo(){
        return startAmmo;
    }
    public void reload(int newAmmo){
        startAmmo=startAmmo+newAmmo;
        if(startAmmo>=maxAmmo){
            startAmmo=maxAmmo;
        }
    }

    protected abstract Fireable createBullet();

    public Fireable fire(){
        if(startAmmo<=0){
            return null;
        }
        else{
            startAmmo--;
            System.out.println(startAmmo);
            return createBullet();
        }
    }
}
