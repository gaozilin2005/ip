package cat;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {

    @FXML private ImageView displayPicture;
    @FXML private TextFlow bubble;
    @FXML private Text dialogText;

    /**
     * Creates a new {@code DialogBox} containing a message bubble and an avatar image.
     * <p>
     * This constructor loads the {@code DialogBox.fxml} layout, injects the FXML
     * controls, and configures the text and image content. The message text is bound
     * to wrap within the available bubble width, and the display picture is set
     * to the provided avatar image.
     * </p>
     *
     * @param text the message to display inside the dialog bubble
     * @param img  the avatar image representing the speaker
     */
    public DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialogText.setText(text);
        // wrap to bubble width (minus a little inner padding)
        dialogText.wrappingWidthProperty().bind(bubble.widthProperty().subtract(20));

        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    /**
     * Creates a {@code DialogBox} styled as a user message.
     * <p>
     * The dialog box will display the specified text and avatar image,
     * and will be aligned on the right-hand side of the chat window.
     * Additional CSS style classes {@code user-row} (for the row) and
     * {@code user-bubble} (for the message bubble) are applied to
     * differentiate user messages from application messages.
     * </p>
     *
     * @param s the message text to display
     * @param i the avatar image representing the user
     * @return a {@code DialogBox} instance styled as a user message
     */
    // AI-assisted: ChatGPT suggested attaching role-specific CSS classes here
    // so that user messages (purple bubbles) can be styled separately from
    // app messages (pink bubbles).
    public static DialogBox getUserDialog(String s, Image i) {
        DialogBox db = new DialogBox(s, i);
        db.getStyleClass().add("user-row"); // row side
        db.bubble.getStyleClass().add("user-bubble"); // bubble side
        return db;
    }

    /**
     * Creates a {@code DialogBox} styled as an application (Cat) message.
     * <p>
     * The dialog box will display the specified text and avatar image,
     * and is flipped so that the avatar appears on the left-hand side
     * and the message bubble on the right. CSS style classes
     * {@code app-row} (for the row) and {@code app-bubble} (for the
     * message bubble) are applied to visually distinguish application
     * messages from user messages.
     * </p>
     *
     * @param s the message text to display
     * @param i the avatar image representing the application (Cat)
     * @return a {@code DialogBox} instance styled as an application message
     */
    // AI-assisted: ChatGPT guided adding "app-row" and "app-bubble" classes
    // for asymmetric chat layout and pink styling.
    public static DialogBox getCatDialog(String s, Image i) {
        DialogBox db = new DialogBox(s, i);
        db.flip(); // put avatar left
        db.getStyleClass().add("app-row");
        db.bubble.getStyleClass().add("app-bubble");
        return db;
    }
}
