package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Represents a wishlist with a name, budget, a list of tags, and a list of items
public class Wishlist {
    private String name;
    private double budget;
    private List<Item> items = new ArrayList<Item>();

    /*
     * REQUIRES: wishlistName has a non-zero length and wishlistBudget >= 0
     * EFFECTS: constructs a wishlist with name set to wishlistName; budget set to wishlistBudget, if
     *          wishlistBudget is 0, then there is no budget assigned to the wishlist.
     */
    public Wishlist(String wishlistName, double wishlistBudget) {
        this.name = wishlistName;
        if (wishlistBudget > 0) {
            this.budget = wishlistBudget;
        } else {
            this.budget = 0;
        }
    }

    /*
     * REQUIRES: there are no items with the same name in items
     * MODIFIES: this
     * EFFECTS: if the given item is found with a matching name in items
     *              - remove the item from list of items
     *              - return true
     *          otherwise, returns false
     */
    public boolean deleteItem(String name) {
        for (int k = 0; k < items.size(); k++) {
            if (items.get(k).getName().equals(name)) {
                items.remove(k);
                return true;
            }
        }
        return false;
    }

    /*
     * MODIFIES: this
     * EFFECTS: if there is no item in items with the same name as the given item's name and budget isn't exceeded
     *              - add the given item to the list of items
     *              - return true
     *          - otherwise, returns false
     */
    public boolean addItem(Item item) {
        if (!containsItemName(item.getName()) && !isExceedingBudget()) {
            items.add(item);
            return true;
        }
        return false;
    }

    /*
     * EFFECTS: returns the total costs of all items in the wishlist, accounting for quantity
     */
    public double getTotalCosts() {
        double totalCost = 0;
        for (Item i : items) {
            totalCost += i.getTotalPrice();
        }
        return totalCost;
    }

    /*
     *  EFFECTS: returns the total quantity of individual items
     */
    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (Item i : items) {
            totalQuantity += i.getQuantity();
        }
        return totalQuantity;
    }

    /*
     * MODIFIES: this
     * EFFECTS: if items is empty
     *              - return false
     *          otherwise, remove all elements in items and return true
     */
    public boolean clearAllItems() {
        if (items.size() == 0) {
            return false;
        } else {
            items.removeAll(items);
            return true;
        }
    }

    /*
     * REQUIRES: items is not empty
     * EFFECTS: returns the average cost per item in the wishlist, not accounting for quantity
     */
    public double averageCostPerItem() {
        return getTotalCosts() / items.size();
    }

    /*
     * REQUIRES: items is not empty
     * EFFECTS: returns the average cost per item in the wishlist, accounting for quantity
     */
    public double averageCostPerQuantity() {
        return getTotalCosts() / getTotalQuantity();
    }

    /*
     * REQUIRES: items is not empty
     * EFFECTS: returns the cheapest individual item, ignoring the item's quantity
     *              - if two or more items are tied for the cheapest price, return the first seen in items
     */
    public Item getCheapestIndividualItem() {
        Item cheapestIndividualItem = items.get(0);
        for (Item i : items) {
            if (i.getPrice() < cheapestIndividualItem.getPrice()) {
                cheapestIndividualItem = i;
            }
        }
        return cheapestIndividualItem;
    }

    /*
     * REQUIRES: items is not empty
     * EFFECTS: returns the most expensive individual item, ignoring the item's quantity
     *              - if two or more items are tied for the most expensive price, return the first seen in items
     */
    public Item getExpensiveIndividualItem() {
        Item expensiveIndividualItem = items.get(0);
        for (Item i : items) {
            if (i.getPrice() > expensiveIndividualItem.getPrice()) {
                expensiveIndividualItem = i;
            }
        }
        return expensiveIndividualItem;
    }

    /*
     * REQUIRES: items is not empty
     * EFFECTS: returns the cheapest item, accounting for quantity
     *              - if two or more items are tied for the cheapest price, return the first seen in items
     */
    public Item getCheapestItem() {
        Item cheapestItem = items.get(0);
        for (Item i : items) {
            if (i.getTotalPrice() < cheapestItem.getTotalPrice()) {
                cheapestItem = i;
            }
        }
        return cheapestItem;
    }

    /*
     * REQUIRES: items is not empty
     * EFFECTS: returns the most expensive item, accounting for quantity
     *              - if two or more items are tied for the most expensive price, return the first seen in items
     */
    public Item getExpensiveItem() {
        Item expensiveItem = items.get(0);
        for (Item i : items) {
            if (i.getTotalPrice() > expensiveItem.getTotalPrice()) {
                expensiveItem = i;
            }
        }
        return expensiveItem;
    }

    /*
     * EFFECTS: returns true if the total costs in the wishlist exceeds the budget, unless the budget is 0
     *              - if budget is 0, always return false
     */
    public boolean isExceedingBudget() {
        if (budget == 0) {
            return false;
        } else {
            return getTotalCosts() > budget;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: sorts items by item price in ascending order and returns items; in the case where two items are the
     *          same price, no change in order occurs
     */
    public List<Item> sortByPrice() {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item i1, Item i2) {
                return Double.valueOf(i1.getTotalPrice()).compareTo(i2.getTotalPrice());
            }
        });
        return items;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sort items by individual price in ascending order and returns items; in the case where two items are
     *          the same price, no change in order occurs
     */
    public List<Item> sortByIndividualPrice() {
        Collections.sort(items, new Comparator<Item>() {
            @Override
            public int compare(Item i1, Item i2) {
                return Double.valueOf(i1.getPrice()).compareTo(i2.getPrice());
            }
        });
        return items;
    }

    /*
     * EFFECTS: returns true if there is an item in items whose name matches the given name, false otherwise
     */
    public boolean containsItemName(String name) {
        for (Item i : items) {
            if (i.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /*
     * EFFECTS: returns the index of the item with a matching name, -1 if the item can't be found
     */
    public int getIndexNum(String name) {
        for (int k = 0; k < items.size(); k++) {
            if (items.get(k).getName().equals(name)) {
                return k;
            }
        }
        return -1;
    }

    public String getName() {
        return name;
    }

    public double getBudget() {
        return budget;
    }

    public int getSize() {
        return items.size();
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
     * MODIFIES: this
     * EFFECTS: if budget is positive, sets the wishlists budget to budget, otherwise sets the budget of the wishlist
     *          to 0, representing no budget associated to the wishlist
     */
    public void setBudget(double budget) {
        if (budget > 0) {
            this.budget = budget;
        } else {
            this.budget = 0;
        }
    }
}
