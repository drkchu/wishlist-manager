package ui;

import model.Wishlist;

import javax.swing.*;
import java.awt.*;

// Class that represents the progress bar that represents how much budget is left for the wishlist
public class BudgetApprovedBar extends JProgressBar {
    Wishlist wishlist;

    /*
     * EFFECTS: creates a progress bar that represents the amount of budget remaining in the wishlist
     */
    public BudgetApprovedBar(Wishlist wishlist) {
        this.wishlist = wishlist;
        this.setStringPainted(true);
        this.setBorderPainted(true);
        this.setPreferredSize(new Dimension(300, 40));
        this.setFont(new Font("N/A", Font.BOLD, 12));
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the bar based on the amount of budget remaining in the wishlist
     */
    public void updateBar() {
        this.setValue((int)((wishlist.getTotalCosts() / wishlist.getBudget()) * 100));
        this.setString("");
        if (wishlist.getBudget() == 0) {
            this.setValue(0);
            this.setString("No Budget!");
        } else if (wishlist.getBudget() == wishlist.getTotalCosts()) {
            this.setValue(100);
            this.setString("Maximum budget achieved!");
        } else if (wishlist.isExceedingBudget()) {
            this.setValue(100);
            this.setString("Budget not approved!");
        }
        this.setVisible(true);
    }
}
