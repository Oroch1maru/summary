package sk.tuke.gamestudio.game.dots.core;

public enum Direction {
    NORTH (0, 1),
    EAST (1, 0),
    SOUTH (0, -1),
    WEST (-1, 0);

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

    public static Direction fromString(char directionString) {
        if(directionString == 'N'){
            return NORTH;
        }
        else if(directionString == 'E'){
            return EAST;
        }
        else if(directionString == 'S'){
            return SOUTH;
        }
        else if(directionString == 'W'){
            return WEST;
        }
        else {
            throw new IllegalArgumentException("Invalid direction: " + directionString);
        }
    }

}
