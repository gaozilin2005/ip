package cat;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * JavaFX entry point for the Cat task manager.
 * <p>
 * This class is a thin UI layer: it displays messages from {@link Cat}
 * and forwards user input to {@link Cat#respond(String)}. All application
 * logic and persistence remain in {@code Cat}.
 */
public class Main extends Application {
    private Cat cat;

    @Override
    public void start(Stage stage) {
        cat = new Cat();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            // Load base UI styles first
            scene.getStylesheets().add(Main.class.getResource("/css/main.css").toExternalForm());

            // Load dialog/bubble styles AFTER so they win if there are overlaps
            scene.getStylesheets().add(Main.class.getResource("/css/dialog-box.css").toExternalForm());

            stage.setTitle("Cat");
            stage.setScene(scene);

            stage.setMinHeight(220);
            stage.setMinWidth(417);

            fxmlLoader.<MainWindow>getController().setCat(cat); // inject the Cat instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
