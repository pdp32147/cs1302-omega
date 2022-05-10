package cs1302.api;

import cs1302.omega.PokeNameResponse;

import java.util.Random;

import java.net.http.HttpClient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

/**This class is used to get the correct pokemon given a name.
 */
public class PokeImage {

    /** HTTP client. */
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
        .build();                                     // builds and returns a HttpClient object

    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    private static final String POKE_API  =
        "https://pokeapi.co/api/v2/pokemon-form/";
    public static String name;
    public static String uri;
    public static String imageUrl;

    /**
     * Get the picture url that corresponds to the pokemon.
     * @return string which is the url
     */
    public static String pictureGet(String date) {
        try {

            name = PokeApi.getName(date);
            uri = POKE_API + name;

            // build request
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
            // send request / receive response in the form of a String
            HttpResponse<String> response = HTTP_CLIENT
                .send(request, BodyHandlers.ofString());
            // ensure the request is okay
            if (response.statusCode() != 200) {
                throw new IOException(response.toString());
            } // if
            // get request body (the content we requested), as a string
            String jsonString = response.body();
            //parse the JSON-formatted string using GSON
            PokeNameResponse pokeNameResponse = GSON
                .fromJson(jsonString, PokeNameResponse.class);
            imageUrl =  pokeNameResponse.sprites.frontDefault;
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
            e.printStackTrace();
        } // trycatch
        return imageUrl;
    } //pictureGet

    /** This method returns an Image object of the pokemon rather than imageview.
     * @return Image of pokemon.
     */
    public static Image pokeForApp(String date) {
        String url = pictureGet(date);
        Image pokemonPic = new Image (url, 200, 200, false, false);
        System.out.println("This is the button generated one");
        return pokemonPic;
    } //pokeForApp

    public static Image pokeForApp() {
        String url = "https://raw.githubusercontent.com/PokeAPI"
            + "/sprites/master/sprites/pokemon/129.png";
        Image pokemonPic = new Image (url, 200, 200, false, false);
        return pokemonPic;
    } //pokeForApp

} //PokeApi
