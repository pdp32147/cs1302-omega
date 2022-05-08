package cs1302.omega;

import cs1302.api.APODApi;
import cs1302.api.PokeApi;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This app allows the user to input their birthday. From the birthday, it will
 * find the Astronomy Picture of the day, and then look at what colors show up
 * the most in the picture in order to assign them a birthday pokemon.
 */
public class OmegaApp extends Application {

    Stage stage;
    Scene scene;

    /**
     * Constructs an {@code OmegaApp} object. This default (i.e., no argument)
     * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
     */
    public OmegaApp() {
    } //OmegaApp

    /** {@inheritDoc} */
    @Override
    public void start(Stage stage) {

        // setup scene
        VBox root = new VBox();
        Scene scene = new Scene(root);

        root.getChildren().addAll(APODApi.create());

//        PokeApi.pictureGet();

        // setup stage
        stage.setTitle("OmegaApp!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();

    } // start

} // OmegaApp
