package persistence;

import org.json.JSONObject;

// Interface that ensures classes that implement it have a means to convert to JSON
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
