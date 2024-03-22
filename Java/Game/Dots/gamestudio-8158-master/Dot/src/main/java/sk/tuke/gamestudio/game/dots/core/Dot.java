package sk.tuke.gamestudio.game.dots.core;

public abstract class Dot {
    private final Color color;
    private int row;
    private int col;
    private DotState state = DotState.NORMAL;



    public DotState getState() {
        return state;
    }

    public Color getColor() {
        return color;
    }


    void setState(DotState newState){
        state=newState;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Dot(Color color, int row, int column) {
        this.color = color;
        this.row=row;
        this.col=column;
    }
}
