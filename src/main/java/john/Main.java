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
            stage.setTitle("John - Your Personal Butler");
            stage.setMinWidth(350);
            stage.setMinHeight(400);
            fxmlLoader.<MainWindow>getController().setJohn(john);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the application in GUI mode by default, or CLI mode if --cli argument is provided.
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
