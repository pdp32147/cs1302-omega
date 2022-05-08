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
 * to the user's birthday. It will also get the predominant color in the picture.
 * If the url supplies a picture that is not an acceptable format for PixelFormat, it will
 * move onto the next date.
 */
public class APODApi {

    public static ImageView picture;
    public static Image apodImage;

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
    private static String date = "2022-02-08"; //Date in format YEAR-MO-DA
    public static String uri = APOD_API + date + hd;

    //Imageview imageView = new ImageView;

    /**
     * Queries the API, get the response, and uses it to assign ImageView picture to a picture.
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
            picture = imgCraft(apodResponse); // assign imageView from query to Picture.
        } catch (IOException | InterruptedException e) {
            System.err.println(e);
            e.printStackTrace();
        } // trycatch
    } //pictureGet

    /** Creates an ImageView object using the response from the pictureGet method.
     * @param apodResponse the url of this query is used for the image
     * @return ImageView of url supplied.
     */
    private static ImageView imgCraft(APODResponse apodResponse) {
        String url  = apodResponse.url;
        apodImage = new Image(url, DEFAULT_WIDTH, DEFAULT_HEIGHT, false, false );
        getCommonColor(apodImage);
        ImageView imgView = new ImageView(apodImage);
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
        int x = 100;
        int y = 100;
        int colorValue  = image.getPixelReader().getArgb(x,y); //get int RGB value

        String hexColor = String.format("%06X", (0xFFFFFF & colorValue)); // convert to hexcode
        int[] rgb = getRGB(hexColor); //convert to array of RGB values

        int red = rgb[0];
        int green = rgb[1];
        int blue = rgb[2];

        System.out.println(red + ", " + green + ", " + blue);

        System.out.println(closestColor(rgb));

        return colorValue;
    } //getCommonColor

    public static String closestColor(int[] rgb) {

        int[] colors = new int[10];
        String[] colorNames = {"red", "blue" , "yellow", "green",
                             "black", "brown", "purple", "gray", "white", "pink"};
        int closer = 1;

        colors[0] = rgbDistance("red", rgb, 225, 0, 0);
        colors[1] = rgbDistance("blue", rgb, 0, 225, 0);
        colors[2] = rgbDistance("yellow", rgb, 225, 225, 0);
        colors[3] = rgbDistance("green", rgb, 0, 225, 0);
        colors[4] = rgbDistance("black", rgb, 0, 0, 0);
        colors[5]= rgbDistance("brown", rgb, 102, 51, 0);
        colors[6]= rgbDistance("purple", rgb, 153, 51, 255);
        colors[7]= rgbDistance("gray", rgb, 160, 160, 160);
        colors[8]= rgbDistance("white", rgb, 225, 225, 225);
        colors[9]= rgbDistance("pink", rgb, 255, 0, 127);

        System.out.println("checkingDistance formula\n");
        for (int i = 0; i <colors.length; i++) {
            System.out.println(colorNames[i] + ": " +colors[i]);
        } // for

        System.out.println();

        for (int i = 0; i < colors.length; i++) {
            if (colors[i] < colors[closer]) {
                closer = i;
            } // if
        } // for

        return colorNames[closer];

    } //closestColor

    public static int rgbDistance(String identifier, int[] rgb, int red, int green, int blue) {
        int distance;
        distance = Math.abs(rgb[0] - red) + Math.abs(rgb[1] - green) + Math.abs(rgb[2] - blue);
        return distance;
    } //rgbDistance

    public static int[] getRGB(String rgb) {
        int[] ret = new int[3];
        for (int i = 0; i < 3; i++) {
            ret[i] = Integer.parseInt(rgb.substring(i * 2, i * 2 + 2), 16);
        }
        return ret;
    } //getRGB


} //APODApi
