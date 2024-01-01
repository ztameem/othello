package othello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * This is the App class. It creates the scene and PaneOrganizer.
 */
public class App extends Application {

    /*
     * This is the start method which actually begins the process of creating the game.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("othello");
        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(organizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }
    /*
    * Here is the mainline!
    */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
