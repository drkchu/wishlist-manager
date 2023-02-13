package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Wishlist class
public class WishlistTest {
    Wishlist testWishlist;
    Wishlist testWishlistSortedByPrice;
    Wishlist testWishlistSortedByIndividualPrice;
    Wishlist testNoBudgetWishlist;
    Item testItem1;
    Item testItem2;
    Item testItem3;
    List<Item> sortedItemsByPrice;
    List<Item> sortedItemsByIndividualPrice;

    @BeforeEach
    void runBefore() {
        testWishlist = new Wishlist("School Supplies", 100.00);
        testWishlistSortedByPrice = new Wishlist("School Supplies", 100.00);
        testWishlistSortedByIndividualPrice = new Wishlist("School Supplies", 100.00);
        testNoBudgetWishlist = new Wishlist("Family", 0);
        testItem1 = new Item("Calculator", 1, 15.00);
        testItem2 = new Item("Pens", 20, 1.00);
        testItem3 = new Item("Scissors", 2, 6.00);

        testWishlist.addItem(testItem1);
        testWishlist.addItem(testItem2);
        testWishlist.addItem(testItem3);

        testNoBudgetWishlist.addItem(testItem1);
        testNoBudgetWishlist.addItem(testItem2);
        testNoBudgetWishlist.addItem(testItem3);

        testWishlistSortedByPrice.addItem(testItem3);
        testWishlistSortedByPrice.addItem(testItem1);
        testWishlistSortedByPrice.addItem(testItem2);

        testWishlistSortedByIndividualPrice.addItem(testItem2);
        testWishlistSortedByIndividualPrice.addItem(testItem3);
        testWishlistSortedByIndividualPrice.addItem(testItem1);

        sortedItemsByPrice = new ArrayList<>();
        sortedItemsByPrice.add(testItem3);
        sortedItemsByPrice.add(testItem1);
        sortedItemsByPrice.add(testItem2);

        sortedItemsByIndividualPrice = new ArrayList<>();
        sortedItemsByIndividualPrice.add(testItem2);
        sortedItemsByIndividualPrice.add(testItem3);
        sortedItemsByIndividualPrice.add(testItem1);
    }

    @Test
    void testConstructor() {
        assertEquals("School Supplies", testWishlist.getName());
        assertEquals(100.00, testWishlist.getBudget());
    }

    @Test
    void testConstructorNoBudget() {
        assertEquals("Family", testNoBudgetWishlist.getName());
        assertEquals(0, testNoBudgetWishlist.getBudget());
    }

    @Test
    void testConstructorNegativeBudget() {
        Wishlist testNegativeBudgetWishlist = new Wishlist("Friends", -100.00);
        assertEquals("Friends", testNegativeBudgetWishlist.getName());
        assertEquals(0, testNegativeBudgetWishlist.getBudget());
    }

    @Test
    void testContainsItemNameFound() {
        assertTrue(testWishlist.containsItemName("Scissors"));
    }

    @Test
    void testContainsItemNameNotFound() {
        assertFalse(testWishlist.containsItemName("Socks"));
    }

    @Test
    void testDeleteItemFound() {
        checkInitialItemsState(testWishlist);
        assertTrue(testWishlist.deleteItem("Pens"));
        assertTrue(testWishlist.containsItemName("Calculator"));
        assertFalse(testWishlist.containsItemName("Pens"));
        assertTrue(testWishlist.containsItemName("Scissors"));
        assertEquals(2, testWishlist.getSize());
    }

    @Test
    void testDeleteItemNotFound() {
        checkInitialItemsState(testWishlist);
        assertFalse(testWishlist.deleteItem("Ruler"));
        assertTrue(testWishlist.containsItemName("Calculator"));
        assertTrue(testWishlist.containsItemName("Pens"));
        assertTrue(testWishlist.containsItemName("Scissors"));
        assertEquals(3, testWishlist.getSize());
    }

    @Test
    void testAddItemNoDuplicate() {
        checkInitialItemsState(testWishlist);
        assertTrue(testWishlist.addItem(new Item("Ruler", 3, 4.00)));
        addedSingleNewItemNameSuccess("Ruler", testWishlist);
    }

    @Test
    void testAddItemNoDuplicateMultiple() {
        checkInitialItemsState(testWishlist);
        assertTrue(testWishlist.addItem(new Item("Ruler", 3, 4.00)));
        assertTrue(testWishlist.addItem(new Item("Protractor", 1, 10.00)));
        addedMultipleNewItemNameSuccess("Ruler", "Protractor", testWishlist);
    }

    @Test
    void testAddItemReachesBudget() {
        checkInitialItemsState(testWishlist);
        assertTrue(testWishlist.addItem(new Item("Lunch box", 1, 53.00)));
        addedSingleNewItemNameSuccess("Lunch box", testWishlist);
    }

    @Test
    void testAddItemNoBudgetWishlist() {
        checkInitialItemsState(testNoBudgetWishlist);
        assertTrue(testNoBudgetWishlist.addItem(new Item("Phone", 2, 1000.00)));
        addedSingleNewItemNameSuccess("Phone", testNoBudgetWishlist);
    }

    @Test
    void testAddItemNoDuplicateBudgetExceedsBudget() {
        checkInitialItemsState(testWishlist);
        assertTrue(testWishlist.addItem(new Item("Backpack", 1, 70.00)));
        addedSingleNewItemNameSuccess("Backpack", testWishlist);
    }

    @Test
    void testAddItemNoDuplicateAlreadyExceedingBudget() {
        checkInitialItemsState(testWishlist);
        testWishlist.setBudget(20.00);
        assertTrue(testWishlist.getBudget() != 0 && testWishlist.getBudget() < testWishlist.getTotalCosts());
        assertFalse(testWishlist.addItem(new Item("Markers", 5, 1.25)));
        checkInitialItemsState(testWishlist);
    }

    @Test
    void testAddItemDuplicate() {
        checkInitialItemsState(testWishlist);
        Item duplicateItem = new Item(testWishlist.getItem(0).getName(), 4, 2.00);
        assertFalse(testWishlist.addItem(duplicateItem));
        checkInitialItemsState(testWishlist);
    }

    @Test
    void testGetTotalCosts() {
        checkInitialItemsState(testWishlist);
        assertEquals(47.00, testWishlist.getTotalCosts());
    }

    @Test
    void testGetTotalCostsNoItems() {
        checkInitialItemsState(testWishlist);
        testWishlist.clearAllItems();
        assertEquals(0, testWishlist.getTotalCosts());
    }

    @Test
    void testGetTotalQuantity() {
        checkInitialItemsState(testWishlist);
        assertEquals(23, testWishlist.getTotalQuantity());
    }

    @Test
    void testGetTotalQuantityNoItems() {
        checkInitialItemsState(testWishlist);
        testWishlist.clearAllItems();
        assertEquals(0, testWishlist.getTotalQuantity());
    }

    @Test
    void testClearAllItems() {
        checkInitialItemsState(testWishlist);
        assertTrue(testWishlist.clearAllItems());
        assertEquals(0, testWishlist.getItems().size());
    }

    @Test
    void testClearAllItemsEmpty() {
        Wishlist emptyWishlist = new Wishlist("Christmas", 500);
        assertEquals(0, emptyWishlist.getItems().size());
        assertFalse(emptyWishlist.clearAllItems());
        assertEquals(0, emptyWishlist.getItems().size());
    }

    @Test
    void testAverageCostPerItem() {
        checkInitialItemsState(testWishlist);
        assertEquals((15.00 + 1.00 * 20 + 6.00 * 2) / 3, testWishlist.averageCostPerItem());
    }

    @Test
    void testAverageCostPerQuantity() {
        checkInitialItemsState(testWishlist);
        assertEquals((15.00 + 1.00 * 20 + 6.00 * 2) / 23, testWishlist.averageCostPerQuantity());
    }

    @Test
    void testGetCheapestIndividualItem() {
        checkInitialItemsState(testWishlist);
        assertEquals(testItem2, testWishlist.getCheapestIndividualItem());
    }

    @Test
    void testGetCheapestIndividualItemMultipleCheapest() {
        checkInitialItemsState(testWishlist);
        testWishlist.addItem(new Item("Pencils", 5, 1.00));
        assertTrue(testWishlist.containsItemName("Pencils"));
        assertEquals(testItem2, testWishlist.getCheapestIndividualItem());
    }

    @Test
    void testGetExpensiveIndividualItem() {
        Item expensiveIndividualItem = new Item("Phone", 1, 800.00);
        checkInitialItemsState(testWishlist);
        testWishlist.addItem(expensiveIndividualItem);
        assertEquals(expensiveIndividualItem, testWishlist.getExpensiveIndividualItem());
    }

    @Test
    void testGetExpensiveIndividualItemMultipleExpensive() {
        checkInitialItemsState(testWishlist);
        testWishlist.addItem(new Item("Water bottle", 2, 15.00));
        assertTrue(testWishlist.containsItemName("Water bottle"));
        assertEquals(testItem1, testWishlist.getExpensiveIndividualItem());
    }

    @Test
    void testCheapestItem() {
        checkInitialItemsState(testWishlist);
        assertEquals(testItem3, testWishlist.getCheapestItem());
    }

    @Test
    void testCheapestItemMultipleCheapest() {
        checkInitialItemsState(testWishlist);
        testWishlist.addItem(new Item("Notebooks", 4, 3.00));
        assertTrue(testWishlist.containsItemName("Notebooks"));
        assertEquals(testItem3, testWishlist.getCheapestItem());
    }

    @Test
    void testExpensiveItem() {
        checkInitialItemsState(testWishlist);
        assertEquals(testItem2, testWishlist.getExpensiveItem());
    }

    @Test
    void testExpensiveItemMultipleExpensive() {
        checkInitialItemsState(testWishlist);
        testWishlist.addItem(new Item("Highlighters", 10, 2.00));
        assertTrue(testWishlist.containsItemName("Highlighters"));
        assertEquals(testItem2, testWishlist.getExpensiveItem());
    }

    @Test
    void testIsExceedingBudgetUnderBudget() {
        checkInitialItemsState(testWishlist);
        assertFalse(testWishlist.isExceedingBudget());
    }

    @Test
    void testIsExceedingBudgetAtBudget() {
        checkInitialItemsState(testWishlist);
        testWishlist.setBudget(testWishlist.getTotalCosts());
        assertFalse(testWishlist.isExceedingBudget());
    }

    @Test
    void testIsExceedingBudgetOverBudget() {
        checkInitialItemsState(testWishlist);
        testWishlist.setBudget(testWishlist.getTotalCosts() - 1.00);
        assertTrue(testWishlist.isExceedingBudget());
    }

    @Test
    void testIsExceedingBudgetNoBudget() {
        checkInitialItemsState(testNoBudgetWishlist);
        testNoBudgetWishlist.addItem(new Item("Laptop", 4, 1200.00));
        addedSingleNewItemNameSuccess("Laptop", testNoBudgetWishlist);
        assertFalse(testNoBudgetWishlist.isExceedingBudget());
    }

    @Test
    void testSortByPriceAlreadySorted() {
        checkInitialSortedByPriceItemsState(testWishlistSortedByPrice);
        assertEquals(sortedItemsByPrice, testWishlistSortedByPrice.sortByPrice());
    }

    @Test
    void testSortByPrice() {
        checkInitialItemsState(testWishlist);
        assertEquals(sortedItemsByPrice, testWishlist.sortByPrice());
    }

    @Test
    void testSortByIndividualPriceAlreadySorted() {
        checkInitialSortedByIndividualPriceItemsState(testWishlistSortedByIndividualPrice);
        assertEquals(sortedItemsByIndividualPrice, testWishlistSortedByIndividualPrice.sortByIndividualPrice());
    }

    @Test
    void testSortByIndividualPrice() {
        checkInitialItemsState(testWishlist);
        assertEquals(sortedItemsByIndividualPrice, testWishlist.sortByIndividualPrice());
    }

    @Test
    void testGetIndexNumFound() {
        checkInitialItemsState(testWishlist);
        assertEquals(2, testWishlist.getIndexNum("Scissors"));
    }

    @Test
    void testGetIndexNumNotFound() {
        checkInitialItemsState(testWishlist);
        assertEquals(-1, testWishlist.getIndexNum("N/A"));
    }

    @Test
    void testSetName() {
        assertEquals("School Supplies", testWishlist.getName());
        testWishlist.setName("Groceries");
        assertEquals("Groceries", testWishlist.getName());
    }

    @Test
    void testSetBudget() {
        assertEquals(100.00, testWishlist.getBudget());
        testWishlist.setBudget(200.00);
        assertEquals(200.00, testWishlist.getBudget());
    }

    @Test
    void testSetBudgetNegative() {
        assertEquals(100.00, testWishlist.getBudget());
        testWishlist.setBudget(-200.00);
        assertEquals(0, testWishlist.getBudget());
    }

    private void checkInitialItemsState(Wishlist wishlist) {
        assertEquals(3, wishlist.getSize());
        assertTrue(wishlist.containsItemName(wishlist.getItem(0).getName()));
        assertEquals(15.00, wishlist.getItem(0).getPrice());
        assertEquals(1, wishlist.getItem(0).getQuantity());
        assertTrue(wishlist.containsItemName(wishlist.getItem(1).getName()));
        assertEquals(1.00, wishlist.getItem(1).getPrice());
        assertEquals(20, wishlist.getItem(1).getQuantity());
        assertTrue(wishlist.containsItemName(wishlist.getItem(2).getName()));
        assertEquals(6.00, wishlist.getItem(2).getPrice());
        assertEquals(2, wishlist.getItem(2).getQuantity());
    }

    private void checkInitialSortedByPriceItemsState(Wishlist wishlist) {
        assertEquals(3, wishlist.getSize());
        assertTrue(wishlist.containsItemName(wishlist.getItem(0).getName()));
        assertEquals(6.00, wishlist.getItem(0).getPrice());
        assertEquals(2, wishlist.getItem(0).getQuantity());
        assertTrue(wishlist.containsItemName(wishlist.getItem(1).getName()));
        assertEquals(15.00, wishlist.getItem(1).getPrice());
        assertEquals(1, wishlist.getItem(1).getQuantity());
        assertTrue(wishlist.containsItemName(wishlist.getItem(2).getName()));
        assertEquals(1.00, wishlist.getItem(2).getPrice());
        assertEquals(20, wishlist.getItem(2).getQuantity());
    }

    private void checkInitialSortedByIndividualPriceItemsState(Wishlist wishlist) {
        assertEquals(3, wishlist.getSize());
        assertTrue(wishlist.containsItemName(wishlist.getItem(0).getName()));
        assertEquals(1.00, wishlist.getItem(0).getPrice());
        assertEquals(20, wishlist.getItem(0).getQuantity());
        assertTrue(wishlist.containsItemName(wishlist.getItem(1).getName()));
        assertEquals(6.00, wishlist.getItem(1).getPrice());
        assertEquals(2, wishlist.getItem(1).getQuantity());
        assertTrue(wishlist.containsItemName(wishlist.getItem(2).getName()));
        assertEquals(15.00, wishlist.getItem(2).getPrice());
        assertEquals(1, wishlist.getItem(2).getQuantity());
    }

    private void addedSingleNewItemNameSuccess(String newItemName, Wishlist wishlist) {
        assertTrue(wishlist.containsItemName("Calculator"));
        assertTrue(wishlist.containsItemName("Pens"));
        assertTrue(wishlist.containsItemName("Scissors"));
        assertTrue(wishlist.containsItemName(newItemName));
        assertEquals(4, wishlist.getSize());
    }

    private void addedMultipleNewItemNameSuccess(String newItemName1, String newItemName2, Wishlist wishlist) {
        assertTrue(wishlist.containsItemName("Calculator"));
        assertTrue(wishlist.containsItemName("Pens"));
        assertTrue(wishlist.containsItemName("Scissors"));
        assertTrue(wishlist.containsItemName(newItemName1));
        assertTrue(wishlist.containsItemName(newItemName2));
        assertEquals(5, wishlist.getSize());
    }

}
