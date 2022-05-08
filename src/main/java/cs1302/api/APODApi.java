package cs1302.api;

import cs1302.api.APODResponse;

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
import javafx.scene.image.PixelFormat;
import javafx.scene.paint.Color;
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
public class APODApi {

    public static ImageView picture;

    /** HTTP client. */
    public static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)           // uses HTTP protocol version 2 where possible
        .followRedirects(HttpClient.Redirect.NORMAL)  // always redirects, except from HTTPS to HTTP
        .build();                                     // builds and returns a HttpClient object

    /** Google {@code Gson} object for parsing JSON-formatted strings. */
    public static Gson GSON = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    public static final String DEFAULT_IMG = //default image is the first apod
        "https://api.nasa.gov/planetary/apod?api_key=" +
        "fDLjXx340WUXFwtnRZrpRZKy9LOlQI3ZuYw60jof&date=1995-06-16&hd=true";

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;

    private static final String APOD_API  =
        "https://api.nasa.gov/planetary/apod?api_key"
        + "=fDLjXx340WUXFwtnRZrpRZKy9LOlQI3ZuYw60jof&date=";
    private static String hd = "&hd=true";
    private static String date = "2020-11-14"; //Date in format YEAR-MO-DA
    public static String uri = APOD_API + date + hd;

    //Imageview imageView = new ImageView;

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
            APODResponse apodResponse = GSON
                .fromJson(jsonString, APODResponse.class);
            picture = imgCraft(apodResponse);
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
            e.printStackTrace();
        } // trycatch
    } //pictureGet

    /** Creates an ImageView object using the response.
     * @param apodResponse the url of this query is used for the image
     * @return ImageView of url supplied.
     */
    private static ImageView imgCraft(APODResponse apodResponse) {
        String url  = apodResponse.url;
        Image img = new Image(url, DEFAULT_WIDTH, DEFAULT_HEIGHT, false, false );
        getCommonColor(img);
        ImageView imgView = new ImageView(img);
        imgView.setPreserveRatio(true);
        return imgView;
    } //imgCraft

/** Creates an ImageView object that can be used in the application.
 * @return ImageView object that can be added to the application scene.
 */
    public static ImageView create() {
        pictureGet();
        return picture;
    } //create


    public static int getCommonColor(Image image) {
        int x = 145;
        int y = 150;
        int colorValue  = image.getPixelReader().getArgb(x,y); //get int RGB value

        String hexColor = String.format("%06X", (0xFFFFFF & colorValue)); // convert to hexcode
        int[] rgb = getRGB(hexColor); //convert to array of RGB values

        int red = rgb[0];
        int green = rgb[1];
        int blue = rgb[2];

        System.out.println(red + ", " + green + ", " + blue);

        return colorValue;
    } //getCommonColor

    public static int[] getRGB(String rgb) {
        int[] ret = new int[3];
        for (int i = 0; i < 3; i++) {
            ret[i] = Integer.parseInt(rgb.substring(i * 2, i * 2 + 2), 16);
        }
        return ret;
    } //getRGB


} //APODApi
