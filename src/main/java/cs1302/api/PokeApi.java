package cs1302.api;

import cs1302.api.PokeResponse;
import cs1302.api.PokeResult;

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

/**This class is used to get the correct APOD Image corrresponding
 * to the user's birthday.
 */
public class PokeApi {

    public static String[] names = new String[200];

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
        "https://pokeapi.co/api/v2/pokemon-color/";
    private static String color = "yellow";
    public static String uri = POKE_API + color;

    /**
     * Get the picture that corresponds to the right date.
     */
    public static void pictureGet() {
        try {
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
            // parse the JSON-formatted string using GSON
            //          PokeResponse pokeResponse = GSON
            //  .fromJson(jsonString, PokeResponse.class);
            System.out.println(jsonString);
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
            e.printStackTrace();
        } // trycatch
    } //pictureGet

    /** Creates an ImageView object using the response.
     * @param apodResponse the url of this query is used for the image
     * @return ImageView of url supplied.
     */
/*    public static String stringArray(int i, PokeResponse pokeResponse) {
        PokeResult result = pokeResponse.pokemon_species[i];
        String name = result.name;
        return name;
  } //imgCraft
*/

} //PokeApi
