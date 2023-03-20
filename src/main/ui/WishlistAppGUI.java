package ui;

import model.Item;
import model.Wishlist;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

// Wishlist application to create, manage, save, and load a wishlist with graphical components!
public class WishlistAppGUI extends JFrame implements ActionListener {
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final Color BACKGROUND_COLOR = new Color(35,47,62);
    public static final Color TEXT_COLOR = new Color(255,153,0);
    public static final Color ITEM_DISPLAY_COLOR = new Color(20,110,180);

    private static final String JSON_STORE = "./data/wishlist.json";
    private static final String ICON_STORE = "./data/wishlistManagerIcon.png";

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Wishlist wishlist;
    private Scanner scan;

    private JButton addItemButton;
    private JButton sortByPriceButton;

    private JList<Item> itemDisplay;

    /*
     * EFFECTS: initializes the JFrame and creates json reader/writer
     */
    public WishlistAppGUI() {
        super("Wishlist Manager");
        initializeWishlist();
        initializeFrame();
        initializeButtons();
        displayItems();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        this.setVisible(true);
        this.pack();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the buttons to add an item and sort items
     */
    private void initializeButtons() {
        addItemButton = new JButton();
        addItemButton.setText("Add an item!");
        addItemButton.addActionListener(this);
        addItemButton.setFont(new Font("", Font.BOLD, 12));
        this.add(addItemButton);

        sortByPriceButton = new JButton();
        sortByPriceButton.setText("Sort items by price!");
        sortByPriceButton.addActionListener(this);
        sortByPriceButton.setFont(new Font("", Font.BOLD, 12));
        this.add(sortByPriceButton);

        // todo, add a thing that deletes a selected item or purchases a selected item
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the parameters for the JFrame and generates the title and budget strings
     */
    private void initializeFrame() {
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        this.setLayout(new FlowLayout());
        generateTitle();
        generateBudget();
    }

    /*
     * MODIFIES: this
     * EFFECTS: generates the budget string
     */
    private void generateBudget() {
        JLabel budget = new JLabel();
        if (wishlist.getBudget() == 0) {
            budget.setText("Budget: N/A");
        } else {
            budget.setText("Budget: $" + wishlist.getBudget());
        }
        budget.setFont(new Font("", Font.ITALIC, 20));
        this.add(budget);
    }

    /*
     * MODIFIES: this
     * EFFECTS: generates the wishlist name string
     */
    private void generateTitle() {
        JLabel title = new JLabel(wishlist.getName());
        title.setIcon(new ImageIcon(ICON_STORE));
        title.setForeground(TEXT_COLOR);
        title.setFont(new Font("", Font.BOLD, 36));
        this.add(title);
    }

    /*
     * MODIFIES: wishlist
     * EFFECTS: creates a new wishlist based on user input for name and budget
     */
    private void initializeWishlist() {
        String wishlistName = JOptionPane.showInputDialog("What would you like to call your wishlist?");
        wishlist = new Wishlist(wishlistName, receiveBudget());
    }

    /*
     * EFFECTS: receive budget from user input
     */
    private Double receiveBudget() {
        try {
            return Double.parseDouble(JOptionPane.showInputDialog("What would you like to set as your budget"));
        } catch (Exception e) {
            return receiveBudget();
        }
    }

    /*
     * REQUIRES: text inputted to quantityField is a valid integer
     *           text inputted to priceField is a valid double
     * MODIFIES: wishlist
     * EFFECTS: creates an item based on user input and adds it to the current wishlist
     */
    private void makeItem() {
        JTextField nameField = new JTextField(10);
        JTextField quantityField = new JTextField(5);
        JTextField priceField = new JTextField(5);

        JPanel itemPanel = new JPanel();
        itemPanel.add(new JLabel("Name:"));
        itemPanel.add(nameField);
        itemPanel.add(Box.createHorizontalStrut(12));
        itemPanel.add(new JLabel("Quantity:"));
        itemPanel.add(quantityField);
        itemPanel.add(Box.createHorizontalStrut(12));
        itemPanel.add(new JLabel("Price: $"));
        itemPanel.add(priceField);

        int result = JOptionPane.showConfirmDialog(null, itemPanel,
                "Please enter an item!", JOptionPane.OK_CANCEL_OPTION);

        if (result == 0) {
            wishlist.addItem(new Item(nameField.getText(), Integer.parseInt(quantityField.getText()),
                    Double.parseDouble(priceField.getText())));
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the state of the GUI, specifically deactivate the addItemButton if budget is exceeded
     *          and updates the visual components
     */
    private void update() {
        addItemButton.setEnabled(!wishlist.isExceedingBudget());
        remove(itemDisplay);
        displayItems();
        revalidate();
        this.pack();
    }

    /*
     * MODIFIES: this
     * EFFECTS: displays the current items in the wishlist with a string representation with each item's
     *          name, price, quantity, and status
     */
    private void displayItems() {
        Item[] itemArray = new Item[wishlist.getItems().size()];
        for (int k = 0; k < wishlist.getItems().size(); k++) {
            itemArray[k] = wishlist.getItem(k);
        }
        itemDisplay = new JList<Item>(itemArray);
        itemDisplay.setBackground(ITEM_DISPLAY_COLOR);
        itemDisplay.setName("Items:");
        this.getContentPane().add(itemDisplay);
    }

    /*
     * MODIFIES: this, wishlist
     * EFFECTS: processes user interaction and updates the state
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addItemButton) {
            try {
                makeItem();
                update();
            } catch (Exception ex) {
                System.out.println("Couldn't add the item!");
            }
        } else if (e.getSource() == sortByPriceButton) {
            processSort();
            update();
        }
    }

    /*
     * MODIFIES: this, wishlist
     * EFFECTS: sorts the wishlist based on total price or individual item price, determined by user input
     */
    private void processSort() {
        if (wishlist.getItems().size() > 0) {
            String[] choices = {"Sort by individual item cost", "Sort by total item cost"};
            int result = JOptionPane.showOptionDialog(null, "How would you like to "
                            + "sort the items in " + wishlist.getName() + "?", "Choose an option!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choices,
                    choices[0]);
            if (result == 0) {
                wishlist.sortByIndividualPrice();
            } else if (result == 1) {
                wishlist.sortByPrice();
            }
        } else {
            JOptionPane.showMessageDialog(null, "There's items to sort!");
        }
    }
}
