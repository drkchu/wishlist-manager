package ui;

import javax.swing.*;

public class ImageDisplay extends JFrame {
    JPanel imagePanel = new JPanel();
    JLabel imageLabel = new JLabel();

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
