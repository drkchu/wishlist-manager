package persistence;

import model.Item;
import model.ItemStatus;
import model.Wishlist;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Wishlist wl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWishlist.json");
        try {
            Wishlist wl = reader.read();
            assertEquals("School Supplies", wl.getName());
            assertEquals(0, wl.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWishlist.json");
        try {
            Wishlist wl = reader.read();
            assertEquals("School Supplies", wl.getName());
            List<Item> items = wl.getItems();
            assertEquals(3, items.size());
            checkItem("Pencils", 10, 0.25, ItemStatus.PENDING, items.get(0));
            checkItem("Notebooks", 3, 2.00, ItemStatus.PENDING, items.get(1));
            checkItem("Calculator", 1, 45.00, ItemStatus.PURCHASED, items.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
