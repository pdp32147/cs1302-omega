package cs1302.omega;

import cs1302.api.APODApi;
import cs1302.api.PokeApi;
import cs1302.api.PokeImage;
import cs1302.omega.AppAlert;

import java.io.IOException;
import java.lang.IllegalArgumentException;


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

    public static String date = "2017-03-12"; //year,month, date, DO NOT MOVE

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
        this.label = new Label();

        ImageView APODContainer = new ImageView();
        ImageView PokeContainer1 = new ImageView();
        ImageView PokeContainer2 = new ImageView();
        ImageView PokeContainer3 = new ImageView();

        APODContainer.setImage(APODApi.apodForApp("2017-03-12", false));
        PokeContainer1.setImage(PokeImage.pokeForApp());  //default zero param
        PokeContainer2.setImage(PokeImage.pokeForApp());
        PokeContainer3.setImage(PokeImage.pokeForApp());


        button.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    showInputTextDialog();
                    String userInput = input;
                    date = userInput;
                    APODContainer.setImage(APODApi.apodForApp(date, true));
                    PokeContainer1.setImage(PokeImage.pokeForApp(date,false));
                    PokeContainer2.setImage(PokeImage.pokeForApp(date,false));
                    PokeContainer3.setImage(PokeImage.pokeForApp(date,false));
                } //handle
            }); //setOnAction

        VBox pokeStack = new VBox();
        pokeStack.getChildren().addAll(PokeContainer1, PokeContainer2, PokeContainer3);
        HBox imageView = new HBox();

        imageView.getChildren().addAll( APODContainer, pokeStack);

        root.getChildren().addAll(button,imageView);

        // setup stage
        stage.setTitle("OmegaApp Presents: Celestial Pokemon!!!");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> Platform.exit());
        stage.setHeight(600);
        stage.setWidth(800);
        stage.show();
        Platform.runLater(() -> stage.setResizable(false));
    } // start



    /** This method helps create the inputtextdialog box.
     */
    private void showInputTextDialog() {

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Enter input");
        dialog.setHeaderText("Please enter the desired date!\n\n"
            + " Format (year-month-day, 0000-00-00)\n\n Example: 2018-12-31");
        dialog.setContentText("Date:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(date -> {
            input = date;
        });
    }

} // OmegaApp
