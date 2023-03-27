package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads a wishlist from JSON data stored in a file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads wishlist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Wishlist read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loading wishlist from file..."));
        return parseWishlist(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses wishlist from JSON object and returns it
    private Wishlist parseWishlist(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Double budget = jsonObject.getDouble("budget");
        Wishlist wl = new Wishlist(name, budget);
        addItems(wl, jsonObject);
        return wl;
    }

    // MODIFIES: wl
    // EFFECTS: parses items from JSON object and adds them to the wishlist
    private void addItems(Wishlist wl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(wl, nextItem);
        }
    }

    // MODIFIES: wl
    // EFFECTS: parses item from JSON object and adds it to the wishlist
    private void addItem(Wishlist wl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int quantity = jsonObject.getInt("quantity");
        double price  = jsonObject.getDouble("price");
        ItemStatus status = ItemStatus.valueOf(jsonObject.getString("status"));
        Item item = new Item(name, quantity, price);
        item.setStatus(status);
        wl.addItem(item);
    }
}
