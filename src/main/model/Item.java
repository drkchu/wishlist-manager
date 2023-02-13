package model;

import static model.ItemStatus.*;

// Represents an item with a name, quantity, price (in dollars), and a status
public class Item {
    private String name;
    private int quantity;
    private double price;
    private ItemStatus status = PENDING;

    /*
     * REQUIRES: itemName has a non-zero length and quantity > 0
     * EFFECTS: name of the item is set to itemName; price is set to itemPrice;
     *          number of items to purchase set to quantity; default status is PENDING
     *          if itemPrice is >= 0, then price is set to itemPrice, otherwise price is 0
     */
    public Item(String itemName, int quantity, double itemPrice) {
        this.name = itemName;
        this.quantity = quantity;
        if (itemPrice >= 0) {
            this.price = itemPrice;
        } else {
            this.price = 0;
        }
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    /*
     * EFFECTS: Returns the total price associated to an object
     */
    public double getTotalPrice() {
        return quantity * price;
    }

    // EFFECTS: returns a string representation of item
    @Override
    public String toString() {
        return quantity + "x " + name + " @ $" + price + " Status: " + status;
    }
}
