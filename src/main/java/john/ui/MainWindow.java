package john.ui;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import john.John;
import john.JohnException;
import john.util.UserNameUtil;


/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    private John john;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/UserProfilePic.png"));
    private Image johnImage = new Image(this.getClass().getResourceAsStream("/images/JohnProfilePic.png"));
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;


    /**
     * Injects the John instance and shows the welcome message.
     *
     * @param john The John instance
     */
    public void setJohn(John john) {
        this.john = john;
        String welcome = Ui.getWelcomeMessage();
        dialogContainer.getChildren().add(
            DialogBox.getJohnDialog(welcome, johnImage)
        );
    }

    /**
     * Sets the username in the input prompt.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String userName = UserNameUtil.getUserName();
        userInput.setPromptText(userName + ", how may I help you?");
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * John's reply and then appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        dialogContainer.getChildren().add(DialogBox.getUserDialog(input, userImage));

        try {
            String response = john.getResponse(input);
            dialogContainer.getChildren().add(DialogBox.getJohnDialog(response, johnImage));
        } catch (Exception e) {
            // Display error with special styling
            String errorMessage = (e instanceof JohnException)
                    ? e.getMessage()
                    : "An unexpected error occurred: " + e.getMessage();
            dialogContainer.getChildren().add(DialogBox.getErrorDialog(errorMessage, johnImage));
        }

        userInput.clear();

        if (john.isLastCommandExit()) {
            // Disable user input and send button
            userInput.setDisable(true);
            sendButton.setDisable(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
    }
}
