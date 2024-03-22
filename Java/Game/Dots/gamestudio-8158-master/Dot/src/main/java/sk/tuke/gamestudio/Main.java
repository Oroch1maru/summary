package sk.tuke.gamestudio;

import sk.tuke.gamestudio.game.dots.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.dots.core.Field;

public class Main {
    public static void main(String[] args) {
        var field = new Field();
        var ui = new ConsoleUI(field);
        ui.play();
    }
}