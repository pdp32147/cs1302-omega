package cs1302.omega;

import cs1302.api.APODApi;
import cs1302.api.PokeApi;
import cs1302.api.PokeImage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
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

    Label label;
    String input;
    String inputStorage;

    public static String date = "2011-03-24"; //year,month, date, DO NOT MOVE

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
        Button button = new Button("\t\t\t\t\t  Click Here to enter "
            + "another date and get another pokemon!!!\t\t\t\t\t");

        ImageView APODContainer = new ImageView();
        ImageView PokeContainer = new ImageView();

        APODContainer.setImage(APODApi.apodForApp("2011-03-24"));
        PokeContainer.setImage(PokeImage.pokeForApp());  //default zero param

        this.label = new Label();

        button.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    showInputTextDialog();
                    String userInput = input;
                    date = userInput;
                    APODContainer.setImage(APODApi.apodForApp(date));
                    PokeContainer.setImage(PokeImage.pokeForApp(date));

                }
            });

        HBox imageView = new HBox();
        Label information = new Label("This APOD is from, the dominant color is, the PokeMon is");

        imageView.getChildren().addAll( APODContainer, PokeContainer);

        root.getChildren().addAll(button,imageView, information);

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
        dialog.setHeaderText("Please enter the desired date!\n\n"
            + " Format (year-month-day, 0000-00-00)\n\n Example: 2001-12-31");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            this.label.setText(name);
            input = name;
        });
        System.out.println("Testing input from helper method: " + input);

    }

} // OmegaApp
