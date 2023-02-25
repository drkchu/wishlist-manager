package persistence;

import model.Item;
import model.ItemStatus;

import static model.ItemStatus.PENDING;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkItem(String name, int quantity, double price, ItemStatus status, Item item) {
        assertEquals(name, item.getName());
        assertEquals(quantity, item.getQuantity());
        assertEquals(price, item.getPrice());
        assertEquals(status, item.getStatus());
    }
}