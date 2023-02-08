package model;

import static model.ItemStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemTest {
    private Item testItem;

    @BeforeEach
    void runBefore() {
        testItem = new Item("Blender", 2, 35.50);
    }

    @Test
    void testConstructor() {
        assertEquals("Blender", testItem.getName());
        assertEquals(2, testItem.getQuantity());
        assertEquals(35.50, testItem.getPrice());
        assertEquals(PENDING, testItem.getStatus());
    }

    @Test
    void testConstructorZeroPrice() {
        testItem = new Item("Wood", 4, 0);
        assertEquals("Wood", testItem.getName());
        assertEquals(4, testItem.getQuantity());
        assertTrue(testItem.getPrice() == 0);
        assertEquals(PENDING, testItem.getStatus());
    }

    @Test
    void testConstructorNegPrice() {
        testItem = new Item("Garbage", 12, -20);
        assertEquals("Garbage", testItem.getName());
        assertEquals(12, testItem.getQuantity());
        assertTrue(testItem.getPrice() == 0);
        assertEquals(PENDING, testItem.getStatus());
    }

    @Test
    void testTotalPrice() {
        assertEquals(71.0, testItem.getTotalPrice());
    }
}