package john.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A DialogBox is a custom control representing a dialog box consisting of an
 * ImageView to represent the speaker's face and a label containing text from the
 * speaker.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with the specified text and image.
     *
     * @param text The text to display in the dialog box
     * @param img  The image to display in the dialog box
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        dialog.setMinHeight(Region.USE_PREF_SIZE);
        dialog.setMaxWidth(Double.MAX_VALUE);
        dialog.setPrefWidth(Region.USE_COMPUTED_SIZE);
        dialog.setWrapText(true);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the
     * right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Applies styling for bot messages.
     */
    private void applyBotStyle() {
        dialog.getStyleClass().clear();
        dialog.getStyleClass().add("bot-label");
        this.getStyleClass().add("bot-dialog");
    }

    /**
     * Applies styling for user messages.
     */
    private void applyUserStyle() {
        dialog.getStyleClass().clear();
        dialog.getStyleClass().add("user-label");
        this.getStyleClass().add("user-dialog");
    }

    /**
     * Applies styling for error messages.
     */
    private void applyErrorStyle() {
        dialog.getStyleClass().clear();
        dialog.getStyleClass().add("error-label");
        this.getStyleClass().add("error-dialog");
    }

    /**
     * Creates a dialog box for the user.
     *
     * @param text The text to display in the dialog box
     * @param img  The image to display in the dialog box
     * @return A new DialogBox instance for the user
     */
    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.applyUserStyle();
        return db;
    }

    /**
     * Creates a dialog box for John.
     *
     * @param text The text to display in the dialog box
     * @param img  The image to display in the dialog box
     * @return A new DialogBox instance for John
     */
    public static DialogBox getJohnDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.applyBotStyle();
        return db;
    }

    /**
     * Creates a dialog box for error messages from John.
     *
     * @param text The error text to display in the dialog box
     * @param img  The image to display in the dialog box
     * @return A new DialogBox instance for error messages
     */
    public static DialogBox getErrorDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        db.applyErrorStyle();
        return db;
    }
}
