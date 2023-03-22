package ui;

import javax.swing.*;

// Class that represents information of the menu bar associated with the GUI
public class MenuBar extends JMenuBar {
    JMenu menuFile = new JMenu("File");

    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem loadItem = new JMenuItem("Load");
    JMenuItem quitItem = new JMenuItem("Quit");

    // EFFECTS: constructs a menu bar with action listeners paired to the GUI
    public MenuBar(WishlistAppGUI gui) {
        menuFile.add(saveItem);
        menuFile.add(loadItem);
        menuFile.add(quitItem);
        saveItem.addActionListener(gui);
        loadItem.addActionListener(gui);
        quitItem.addActionListener(gui);
        this.add(menuFile);
    }

    public JMenuItem getSaveItem() {
        return saveItem;
    }

    public JMenuItem getLoadItem() {
        return loadItem;
    }

    public JMenuItem getQuitItem() {
        return quitItem;
    }

}
