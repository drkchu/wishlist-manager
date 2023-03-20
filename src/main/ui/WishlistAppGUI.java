package ui;

import model.Wishlist;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.util.Scanner;

public class WishlistAppGUI extends JFrame {
    private static final String JSON_STORE = "./data/wishlist.json";

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Wishlist wishlist;
    private Scanner scan;

    public WishlistAppGUI() {
        public static final int WIDTH = 1080;
        public static final int HEIGHT = 720;

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }
}
