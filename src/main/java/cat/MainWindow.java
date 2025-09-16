package cat;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Cat cat;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/tom.png"));
    private Image catImage = new Image(this.getClass().getResourceAsStream("/images/jerry.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Cat instance */
    public void setCat(Cat c) {
        cat = c;
        String greeting = cat.greeting();
        dialogContainer.getChildren().add(
                DialogBox.getCatDialog(greeting, catImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Cat's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        String response = cat.respond(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getCatDialog(response, catImage)
        );
        userInput.clear();

        if ("bye".equalsIgnoreCase(input.trim())) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> javafx.application.Platform.exit());
            delay.play();
        }
    }
}
