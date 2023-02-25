package persistence;

import model.Item;
import model.ItemStatus;
import model.Wishlist;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for the JSON writer class that saves a wishlist onto the wishlist.json file
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Wishlist wl = new Wishlist("School Supplies", 100.00);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Wishlist wl = new Wishlist("School Supplies", 100.00);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWishlist.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWishlist.json");
            wl = reader.read();
            assertEquals("School Supplies", wl.getName());
            assertEquals(100.00, wl.getBudget());
            assertEquals(0, wl.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Wishlist wl = new Wishlist("School Supplies", 1000.00);
            wl.addItem(new Item("Backpack", 2, 65.00));
            wl.getItem(0).setStatus(ItemStatus.PURCHASED);
            wl.addItem(new Item("Pencil Case", 1, 12.00));
            wl.getItem(1).setStatus(ItemStatus.PURCHASED);
            wl.addItem(new Item("Erasers", 12, 1.25));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWishlist.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWishlist.json");
            wl = reader.read();
            assertEquals("School Supplies", wl.getName());
            assertEquals(1000.00, wl.getBudget());
            List<Item> items = wl.getItems();
            assertEquals(3, items.size());
            checkItem("Backpack", 2, 65.00, ItemStatus.PURCHASED, wl.getItem(0));
            checkItem("Pencil Case", 1, 12.00, ItemStatus.PURCHASED, wl.getItem(1));
            checkItem("Erasers", 12, 1.25, ItemStatus.PENDING, wl.getItem(2));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
