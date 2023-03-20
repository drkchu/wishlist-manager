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

public class WishlistAppGUI extends JFrame implements ActionListener {
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 720;
    public static final Color BACKGROUND_COLOR = new Color(35,47,62);
    public static final Color TEXT_COLOR = new Color(255,153,0);

    private static final String JSON_STORE = "./data/wishlist.json";
    private static final String ICON_STORE = "./data/wishlistManagerIcon.png";

    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Wishlist wishlist;
    private Scanner scan;

    private JButton addItemButton;
    private JList<Item> itemDisplay;

    public WishlistAppGUI() {
        super("Wishlist Manager");
        initializeWishlist();
        wishlist.addItem(new Item("textItem", 1, 1));
        initializeFrame();
        initializeButtons();
        displayItems();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        this.setVisible(true);
        //this.pack();
    }

    private void initializeButtons() {
        addItemButton = new JButton();
        addItemButton.setSize(new Dimension(200, 100));
        addItemButton.setText("Add an item!");
        addItemButton.addActionListener(this);
        this.add(addItemButton);
    }

    private void initializeFrame() {
        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        this.setLayout(new FlowLayout());
        generateTitle();
        generateBudget();
    }

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

    private void generateTitle() {
        JLabel title = new JLabel(wishlist.getName());
        title.setIcon(new ImageIcon(ICON_STORE));
        title.setForeground(TEXT_COLOR);
        title.setFont(new Font("", Font.BOLD, 30));
        this.add(title);
    }

    private void initializeWishlist() {
        String wishlistName = JOptionPane.showInputDialog("What would you like to call your wishlist?");
        wishlist = new Wishlist(wishlistName, receiveBudget());
    }

    private Double receiveBudget() {
        try {
            return Double.parseDouble(JOptionPane.showInputDialog("What would you like to set as your budget"));
        } catch (Exception e) {
            return receiveBudget();
        }
    }

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

    private void update() {
        addItemButton.setEnabled(!wishlist.isExceedingBudget());
        remove(itemDisplay);
        displayItems();
        revalidate();
    }

    private void displayItems() {
        Item[] itemArray = new Item[wishlist.getItems().size()];
        for (int k = 0; k < wishlist.getItems().size(); k++) {
            itemArray[k] = wishlist.getItem(k);
        }
        itemDisplay = new JList<Item>(itemArray);
        itemDisplay.setName("Items:");
        this.getContentPane().add(itemDisplay);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addItemButton) {
            try {
                makeItem();
                update();
            } catch (Exception ex) {
                System.out.println("Couldn't add the item!");
            }
        }
    }
}
