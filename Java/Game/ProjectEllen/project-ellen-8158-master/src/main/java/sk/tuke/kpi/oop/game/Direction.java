package sk.tuke.kpi.oop.game;

public enum Direction {
    NORTH (0, 1),
    EAST (1, 0),
    SOUTH (0, -1),
    WEST (-1, 0),
    NORTHEAST(1, 1),
    SOUTHEAST(1, -1),
    SOUTHWEST(-1, -1),
    NORTHWEST(-1, 1),
    NONE(0, 0);


    private  int dx;
    private int dy;


    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
    }

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction fromAngle(float angle){
        if(angle==0){
            return NORTH;
        }
        else if(angle==45){
            return NORTHWEST;
        }
        else if(angle==90){
            return WEST;
        }
        else if(angle==135){
            return SOUTHWEST;
        }
        else if(angle==180){
            return SOUTH;
        }
        else if(angle==225){
            return SOUTHEAST;
        }
        else if(angle==270){
            return EAST;
        }
        else if(angle==315){
            return NORTHEAST;
        }
        return null;
    }

    public float getAngle() {
       if(this==NORTH) {
            return 0.0f;
        }
       else if(this==EAST){
            return 270.0f;
       }
       else if(this==SOUTH){
            return 180.0f;
       }
       else if(this==WEST){
            return 90.0f;
       }
       else if(this==NORTHEAST) {
            return 315.0f;
       }
       else if(this==SOUTHEAST) {
            return 225.0f;
       }
       else if(this==SOUTHWEST) {
            return 135.0f;
       }
       else if(this==NORTHWEST) {
            return 45.0f;
       }
       return 0.0f;

    }
    public Direction  combine(Direction other){
        int newX=this.getDx();
        int newY=this.getDy();
        if(other==null || other==this){
            return this;
        }
        if(this.getDx()!=other.getDx()){
            newX=newX+ other.getDx();
        }
        if(this.getDy()!=other.getDy()){
            newY=newY+ other.getDy();
        }
        Direction direction = NONE;

        for (Direction value : Direction.values()) {
            if (value.getDx() == newX && value.getDy() == newY)
            {
                direction=value;
            }
        }
        return direction;



    }
}



