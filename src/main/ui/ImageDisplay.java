package ui;

import javax.swing.*;

// Class that displays an image based on the filepath given in the constructor
public class ImageDisplay extends JFrame {
    JPanel imagePanel = new JPanel();
    JLabel imageLabel = new JLabel();

    /**
     * REQUIRES: filePath is a valid path to an image
     * EFFECTS: Creates a JFrame that is displayed to the user upon instantiation with the given image
     */
    public ImageDisplay(String filePath) {
        this.setSize(1280, 720);
        imageLabel.setIcon(new ImageIcon(filePath));
        imagePanel.setSize(1280, 710);
        imagePanel.add(imageLabel);
        this.add(imagePanel);
        this.setLayout(null);
        this.setVisible(true);
        this.toFront();
        this.setAlwaysOnTop(true);
    }
}
