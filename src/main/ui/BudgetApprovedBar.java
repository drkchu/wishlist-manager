package ui;

import model.Wishlist;

import javax.swing.*;
import java.awt.*;

public class BudgetApprovedBar extends JProgressBar {
    Wishlist wishlist;

    public BudgetApprovedBar(Wishlist wishlist) {
        this.wishlist = wishlist;
        this.setStringPainted(true);
        this.setBorderPainted(true);
        this.setPreferredSize(new Dimension(300, 40));
        this.setFont(new Font("N/A", Font.BOLD, 12));
    }

    public void updateBar() {
        this.setForeground(Color.GREEN);
        this.setValue((int)((wishlist.getTotalCosts() / wishlist.getBudget()) * 100));
        this.setString("");
        if (wishlist.getBudget() == 0) {
            this.setValue(0);
            this.setString("No Budget!");
        } else if (wishlist.isExceedingBudget()) {
            this.setValue(100);
            this.setForeground(Color.RED);
            this.setString("Budget not approved!");
        }
        this.setVisible(true);
    }
}
