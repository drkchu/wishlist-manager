package model;

import static model.ItemStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Unit tests for Item class
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

    @Test
    void testSetName() {
        assertEquals("Blender", testItem.getName());
        testItem.setName("Screw Driver");
        assertEquals("Screw Driver", testItem.getName());
    }

    @Test
    void testSetQuantity() {
        assertEquals(2, testItem.getQuantity());
        testItem.setQuantity(4);
        assertEquals(4, testItem.getQuantity());
    }

    @Test
    void testSetPrice() {
        assertEquals(35.50, testItem.getPrice());
        testItem.setPrice(12.00);
        assertEquals(12.00, testItem.getPrice());
    }

    @Test
    void testSetStatus() {
        assertEquals(PENDING, testItem.getStatus());
        testItem.setStatus(PURCHASED);
        assertEquals(PURCHASED, testItem.getStatus());
    }

    @Test
    void testToString() {
        assertEquals("2x Blender @ $35.5 Status: PENDING", testItem.toString());
    }
}