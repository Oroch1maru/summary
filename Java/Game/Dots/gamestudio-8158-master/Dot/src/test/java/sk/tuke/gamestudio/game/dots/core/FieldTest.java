package sk.tuke.gamestudio.game.dots.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldTest {
    private Field field = new Field();

    private boolean checkDuplicates() {
        for (int row = 0; row < field.getRowCount(); row++) {
            for (int column = 0; column < field.getColumnCount(); column++) {
                if (row < field.getRowCount() - 1) {
                    if (pairCheckingDuplicates(row,column,row+1, column)) return true;
                }
                if (row > 0) {
                    if (pairCheckingDuplicates(row,column,row-1, column)) return true;
                }
                if (column < field.getColumnCount() - 1) {
                    if (pairCheckingDuplicates(row,column,row, column+1)) return true;
                }
                if (column > 0) {
                    if (pairCheckingDuplicates(row,column,row, column-1)) return true;
                }
            }
        }
        return false;
    }

    private boolean pairCheckingDuplicates(int row, int column, int nextRow, int nextColumn) {
        return field.getDot(row, column).getColor() == field.getDot(nextRow, nextColumn).getColor();
    }

    @Test
    public void testGoodGenerateField(){
        assertTrue(checkDuplicates());
    }

    @Test
    public void testMarkFirstDot(){
        field.markFirstDot(0,0);
        assertEquals(DotState.MARKED,field.getDot(0,0).getState());
    }

}
