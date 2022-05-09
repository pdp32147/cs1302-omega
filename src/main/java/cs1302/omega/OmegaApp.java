package cs1302.omega;

import cs1302.api.APODApi;
import cs1302.api.PokeApi;
import cs1302.api.PokeImage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Optional;

/**
 * This app allows the user to input their birthday. From the birthday, it will
 * find the Astronomy Picture of the day, and then look at what colors show up
 * the most in the picture in order to assign them a birthday pokemon.
 */
public class OmegaApp extends Application {

    Stage stage;
    Scene scene;

    ImageView pokeView = PokeImage.create();
    ImageView apodView = APODApi.create();

    Label label;

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


        HBox imageView = new HBox();
        Label information = new Label("This APOD is from, the dominant color is, the PokeMon is");

        imageView.getChildren().addAll(APODApi.create(), PokeImage.create());

        root.getChildren().addAll(Components.create(), imageView, information);

        this.label = new Label();



        // setup stage
        stage.setTitle("OmegaApp Presents: Celestial Pokemon!!!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.sizeToScene();
        stage.show();

    } // start



    /** This method helps create the inputtextdialog box.
     */
    private void showInputTextDialog() {

        TextInputDialog dialog = new TextInputDialog("2018-09-26");


        dialog.setTitle("Enter input");
        dialog.setHeaderText("Please enter the desired date."
            + "Format (year-month-day, 0000-00-00)\n Example: 2001-12-31");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            this.label.setText(name);
        });
    }

} // OmegaApp
