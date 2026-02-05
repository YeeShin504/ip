package john;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import john.ui.MainWindow;

/**
 * Main entry point for the John application.
 * Supports both GUI and CLI modes.
 */
public class Main extends Application {

    private John john = new John("data/john.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setJohn(john);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method - entry point for the application.
     * Run with --cli argument for CLI mode, otherwise launches GUI.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("--cli")) {
            // Run in CLI mode
            new John("data/john.txt").run();
        } else {
            // Run in GUI mode
            launch(args);
        }
    }
}
