package sk.tuke.kpi.oop.game;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
//import sk.tuke.kpi.oop.game.scenarios.FirstSteps;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;
import sk.tuke.kpi.oop.game.scenarios.FirstSteps;
import sk.tuke.kpi.oop.game.scenarios.Level;
//import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;

public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);
        Game game = new GameApplication(windowSetup, new LwjglBackend());  // v pripade Mac OS bude druhy parameter "new Lwjgl2Backend()"
        Scene scene = new World("Level", "maps/myLevel.tmx",new Level.Factory());
        game.addScene(scene);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, () -> game.stop());
        //FirstSteps firstSteps = new FirstSteps();
//        MissionImpossible missionImpos = new MissionImpossible();
        //scene.addListener(firstSteps);
        Level escapeRoom=new Level();
        scene.addListener(escapeRoom);

        game.start();
    }
}
