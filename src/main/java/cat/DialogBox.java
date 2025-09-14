package cat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    // AI-assisted: ChatGPT suggested attaching role-specific CSS classes here
    // so that user messages (purple bubbles) can be styled separately from
    // app messages (pink bubbles).
    public static DialogBox getUserDialog(String s, Image i) {
        DialogBox db = new DialogBox(s, i);
        db.getStyleClass().add("user-row");             // row side
        db.bubble.getStyleClass().add("user-bubble");   // bubble side
        return db;
    }

    // AI-assisted: ChatGPT guided adding "app-row" and "app-bubble" classes
    // for asymmetric chat layout and pink styling.
    public static DialogBox getDukeDialog(String s, Image i) {
        DialogBox db = new DialogBox(s, i);
        db.flip();                                      // put avatar left
        db.getStyleClass().add("app-row");
        db.bubble.getStyleClass().add("app-bubble");
        return db;
    }
}
