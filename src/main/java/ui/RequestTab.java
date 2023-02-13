package ui;

import javax.swing.*;
import java.awt.*;

public class RequestTab extends JPanel {
    public RequestTab() {
        BoxLayout mainLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

        JPanel attackTypePanel = new JPanel();
        BoxLayout attackTypeLayout = new BoxLayout(attackTypePanel, BoxLayout.X_AXIS);

        JLabel attackLabel = new JLabel("attack type: ");
        JComboBox attackTypeCombobox = new JComboBox(new String[]{"Sniper", "Cluster bomb", "Pitchfork"});

        attackTypePanel.add(attackLabel);
        attackTypePanel.add(attackTypeCombobox);
        attackTypePanel.setLayout(attackTypeLayout);

        JButton addPositionButton = new JButton("Add $");
        JButton removePositionButton = new JButton("Remove $");
        JTextArea requestTextArea = new JTextArea();
        JPanel requestPanel = new JPanel();

        SpringLayout springLayout = new SpringLayout();
        springLayout.putConstraint(SpringLayout.NORTH, addPositionButton, 5, SpringLayout.NORTH, requestPanel);
        springLayout.putConstraint(SpringLayout.EAST, addPositionButton, -5, SpringLayout.EAST, requestPanel);
        springLayout.putConstraint(SpringLayout.WEST, addPositionButton, 5, SpringLayout.EAST, requestTextArea);

        springLayout.putConstraint(SpringLayout.NORTH, removePositionButton, 5, SpringLayout.SOUTH, addPositionButton);
        springLayout.putConstraint(SpringLayout.EAST, removePositionButton, -5, SpringLayout.EAST, requestPanel);

        springLayout.putConstraint(SpringLayout.NORTH, requestTextArea, 0, SpringLayout.NORTH, requestPanel);
        springLayout.putConstraint(SpringLayout.WEST, requestTextArea, 0, SpringLayout.WEST, requestPanel);
        springLayout.putConstraint(SpringLayout.SOUTH, requestTextArea, 0, SpringLayout.SOUTH, requestPanel);
        springLayout.putConstraint(SpringLayout.EAST, requestTextArea, -5, SpringLayout.WEST, addPositionButton);
        springLayout.putConstraint(SpringLayout.EAST, requestTextArea, -5, SpringLayout.WEST, removePositionButton);


        requestPanel.add(requestTextArea);
        requestPanel.add(addPositionButton);
        requestPanel.add(removePositionButton);
        requestPanel.setLayout(springLayout);


        this.add(attackTypePanel);
        this.add(requestPanel);
        this.setLayout(mainLayout);
    }
}
