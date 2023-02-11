package ui;

import model.Item;
import model.ItemStatus;
import model.Wishlist;

import java.util.Scanner;

// Wishlist application to create and manage a wishlist for all of your shopping needs!
public class WishlistApp {
    private Wishlist wishlist;
    private Scanner scan;

    /*
     * EFFECTS: initializes the user input and runs the wishlist manager application
     */
    public WishlistApp() {
        scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        runWishlistManager();
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user input
     */
    private void runWishlistManager() {
        System.out.println("Welcome to Derek's Wishlist Manager!");
        boolean keepGoing = true;
        String command = null;
        while (keepGoing) {
            displayOptions();
            command = scan.next().toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\n Thanks for using Derek's Wishlist Manager!");
    }

    /*
     * EFFECTS: display the possible options that the user can choose from
     */
    private void displayOptions() {
        System.out.println("\nChoose from the following options:");
        System.out.println("\tw -> create a new wishlist");
        if (wishlist != null) {
            System.out.println("\tn -> add a new item to the wishlist");
            System.out.println("\td -> delete an item in the wishlist");
            System.out.println("\tt -> see the total costs of the wishlist");
            System.out.println("\ta -> see the average price of the wishlist");
            System.out.println("\tv -> view the items in the wishlist");
            System.out.println("\ts -> sort the wishlist based on price");
            System.out.println("\te -> edit/view the wishlist and or the items associated");
        }
        System.out.println("\tq -> quit");
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user commands
     */
    private void processCommand(String command) {
        if (command.equals("w")) {
            doCreateNewWishlist();
        } else if (wishlist != null) {
            if (command.equals("n")) {
                doAddNewItem();
            } else if (command.equals("d")) {
                doDeleteAnItem();
            } else if (command.equals("t")) {
                doViewTotalCosts();
            } else if (command.equals("a")) {
                doViewAverageCost();
            } else if (command.equals("v")) {
                doViewItems();
            } else if (command.equals("s")) {
                doSortWishlist();
            } else if (command.equals("e")) {
                doEditManager();
            } else {
                invalidSelectionMessage();
            }
        } else {
            invalidSelectionMessage();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates a new wishlist based on user input
     */
    private void doCreateNewWishlist() {
        String name;
        Double budget;
        System.out.println("What would you like to call your new Wishlist?");
        name = scan.next();
        System.out.println("What would you like to set as the budget for " + name + "? (Enter 0 for no budget)");
        budget = scan.nextDouble();
        wishlist = new Wishlist(name, budget);
        System.out.println("Successful created new wishlist '" + name + "'!");
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: creates a new item based user input and adds it to the wishlist as long as there is
     */
    private void doAddNewItem() {
        String name;
        int quantity;
        double price;
        if (wishlist.isExceedingBudget()) {
            System.out.println("Sorry, your budget of $" + wishlist.getBudget() + " has been exceeded!");
        } else {
            System.out.println("What's the name of the item you'd like to add?");
            name = scan.next();
            while (wishlist.containsItemName(name) || name.equals("")) {
                if (wishlist.containsItemName(name)) {
                    System.out.println(name + " exists in " + wishlist.getName() + "! Choose a different name!");
                } else if (name.equals("")) {
                    System.out.println("Cannot set an empty string as the name! Choose a different name!");
                }
                name = scan.next();
            }
            System.out.println("What quantity of " + name + " do you need?");
            quantity = scan.nextInt();
            System.out.println("How much does " + name + " cost?");
            price = scan.nextDouble();
            wishlist.addItem(new Item(name, quantity, price));
            System.out.println("Successfully added " + name + " to " + wishlist.getName());
        }
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: removes the item from the wishlist if found
     */
    private void doDeleteAnItem() {
        if (!checkEmptyWishlist()) {
            String deleteItemName;
            System.out.println("What the name of the item you would you like to delete?");
            deleteItemName = scan.next();
            if (wishlist.deleteItem(deleteItemName)) {
                System.out.println(deleteItemName + " was successfully removed from " + wishlist.getName());
            } else {
                System.out.println(deleteItemName + " wasn't found!");
            }
        }
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * EFFECTS: displays the total price
     */
    private void doViewTotalCosts() {
        System.out.println(wishlist.getName() + " has a total price of $" + wishlist.getTotalCosts());
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * EFFECTS: displays the average price per item or average price per individual quantity of items based on the user
     */
    private void doViewAverageCost() {
        String choice = "";
        if (!checkEmptyWishlist()) {
            while (!choice.equals("0") && !choice.equals("1")) {
                System.out.println("\t0 -> per item");
                System.out.println("\t1 -> per individual quantity of items");
                choice = scan.next();
                if (choice.equals("0")) {
                    System.out.println("Average cost per item is $" + wishlist.averageCostPerItem());
                } else if (choice.equals("1")) {
                    System.out.println("Average cost per individual quantity is $" + wishlist.averageCostPerQuantity());
                } else {
                    invalidSelectionMessage();
                }
            }
        }
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * EFFECTS: displays all items in the wishlist
     */
    private void doViewItems() {
        if (!checkEmptyWishlist()) {
            System.out.println("Here are all the items in " + wishlist.getName());
            for (Item i : wishlist.getItems()) {
                System.out.println("\t" + i);
            }
        }
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: sorts all the items in wishlist to ascending order by price, specified by user input
     */
    private void doSortWishlist() {
        String choice = "";
        if (!checkEmptyWishlist()) {
            while (!choice.equals("0") && !choice.equals("1")) {
                System.out.println("\t0 -> sort by total item price");
                System.out.println("\t1 -> sort by individual item quantity price");
                choice = scan.next();
                if (choice.equals("0")) {
                    wishlist.sortByPrice();
                    System.out.println(wishlist.getName() + " has been successfully sorted by total price!");
                } else if (choice.equals("1")) {
                    wishlist.sortByIndividualPrice();
                    System.out.println(wishlist.getName() + " has been successfully sorted by price per quantity!!");
                } else {
                    invalidSelectionMessage();
                }
            }
        }
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: allows the user to modify either the wishlist or the items
     */
    private void doEditManager() {
        String choice = "";
        while (!choice.equals("0") && !choice.equals("1") && !choice.equals("2")) {
            System.out.println(wishlist.getName() + " currently has a budget of $" + wishlist.getBudget());
            System.out.println("\t0 -> edit wishlist attributes");
            System.out.println("\t1 -> edit wishlist items");
            System.out.println("\t2 -> go back");
            choice = scan.next();
            if (choice.equals("0")) {
                doEditWishlist();
            } else if (choice.equals("1")) {
                doEditItems();
            } else if (!choice.equals("2")) {
                invalidSelectionMessage();
            }
        }
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: modifies the wishlist attributes based on user input
     */
    private void doEditWishlist() {
        System.out.println("Would you like to change the name or the budget?");
        String choice = "";
        while (!choice.equals("0") && !choice.equals("1")) {
            System.out.println("\t0 -> change name");
            System.out.println("\t1 -> change budget");
            choice = scan.next();
            if (choice.equals("0")) {
                doEditWishlistName();
            } else if (choice.equals("1")) {
                doEditWishlistBudget();
            } else {
                invalidSelectionMessage();
            }
        }
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: changes the wishlist budget based on user input
     */
    private void doEditWishlistBudget() {
        System.out.println("What would you like to change the budget to? (0 for no budget)");
        double choice = scan.nextDouble();
        wishlist.setBudget(choice);
        System.out.println("Successfully set the budget to $" + wishlist.getBudget());
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: changes the wishlist name based on user input
     */
    private void doEditWishlistName() {
        System.out.println("What would you like to set as the name for your wishlist?");
        String choice = scan.next();
        wishlist.setName(choice);
        System.out.println("Successfully set the name to " + wishlist.getName());
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: changes an item in the item list based on user input
     */
    private void doEditItems() {
        String choice = "";
        if (!checkEmptyWishlist()) {
            if (!wishlist.containsItemName(choice)) {
                doViewItems();
                System.out.println("\tEnter the name of the item you'd like to edit!");
                choice = scan.next();
                if (wishlist.containsItemName(choice)) {
                    doEditItem(wishlist.getItem(wishlist.getIndexNum(choice)));
                } else {
                    invalidSelectionMessage();
                }
            }
        }
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: modifies the item's attributes based on user input
     */
    private void doEditItem(Item item) {
        System.out.println("How would you like to edit item: " + item);
        String choice = "";
        while (!choice.equals("0") && !choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
            System.out.println("\t0 -> change name");
            System.out.println("\t1 -> change quantity");
            System.out.println("\t2 -> change price");
            System.out.println("\t3 -> change status");
            choice = scan.next();
            if (choice.equals("0")) {
                doEditItemName(item);
            } else if (choice.equals("1")) {
                doEditItemQuantity(item);
            } else if (choice.equals("2")) {
                doEditItemPrice(item);
            } else if (choice.equals("3")) {
                doEditItemStatus(item);
            } else {
                invalidSelectionMessage();
            }
        }
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: changes the item name based on user input
     */
    private void doEditItemName(Item item) {
        System.out.println("What would you like to set as the name for this item?");
        String choice = scan.next();
        item.setName(choice);
        System.out.println("Successfully set the name to " + item.getName());
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: changes the item quantity based on user input
     */
    private void doEditItemQuantity(Item item) {
        System.out.println("What would you like to set as the quantity for this item?");
        int choice = scan.nextInt();
        item.setQuantity(choice);
        System.out.println("Successfully set the quantity to " + item.getQuantity());
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: changes the item price based on user input
     */
    private void doEditItemPrice(Item item) {
        System.out.println("What would you like to set as the price for this item?");
        double choice = scan.nextDouble();
        item.setPrice(choice);
        System.out.println("Successfully set the price to $" + item.getPrice());
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * MODIFIES: this
     * EFFECTS: changes the item status based on user input
     */
    private void doEditItemStatus(Item item) {
        System.out.println("What status would you like to set " + item.getName() + "?");
        String choice = "";
        while (!choice.equals("0") && !choice.equals("1")) {
            System.out.println("\t0 -> set to PENDING");
            System.out.println("\t1 -> set to PURCHASED");
            choice = scan.next();
            if (choice.equals("0")) {
                item.setStatus(ItemStatus.PENDING);
                System.out.println("Successfully set " + item.getName() + "'s status to PENDING");
            } else if (choice.equals("1")) {
                item.setStatus(ItemStatus.PURCHASED);
                System.out.println("Successfully set " + item.getName() + "'s status to PURCHASED");
            } else {
                invalidSelectionMessage();
            }
        }
    }

    /*
     * REQUIRES: a wishlist has been instantiated
     * EFFECTS: returns true if the wishlist has no items, false otherwise
     */
    private boolean checkEmptyWishlist() {
        if (wishlist.getItems().size() == 0) {
            System.out.println("There are no items in the wishlist!");
            return true;
        }
        return false;
    }

    /*
     * EFFECTS: prints out an invalid selection message into the console
     */
    private void invalidSelectionMessage() {
        System.out.println("\nInvalid selection! Please try again!");
    }
}
