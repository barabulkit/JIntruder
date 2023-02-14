package ui;

import javax.swing.*;

public class SimpleListOptions  extends JPanel {

    public SimpleListOptions() {
        SpringLayout mainLayout = new SpringLayout();

        JList itemList = new JList();
        JButton loadButton = new JButton("Load");
        JButton removeButton = new JButton("Remove");
        JButton clearButton = new JButton("Clear");
        JButton addButton = new JButton("Add");
        JTextField itemTextField = new JTextField();

        mainLayout.putConstraint(SpringLayout.EAST, itemList, -5, SpringLayout.EAST, this);
        mainLayout.putConstraint(SpringLayout.NORTH, itemList, 5, SpringLayout.NORTH, this);
        mainLayout.putConstraint(SpringLayout.SOUTH, itemList, 5, SpringLayout.SOUTH, this);
        mainLayout.putConstraint(SpringLayout.WEST, itemList, 5, SpringLayout.EAST, removeButton);
        mainLayout.putConstraint(SpringLayout.SOUTH, itemList, -5, SpringLayout.NORTH, itemTextField);

        mainLayout.putConstraint(SpringLayout.NORTH, loadButton, 5, SpringLayout.NORTH, this);
        mainLayout.putConstraint(SpringLayout.WEST, loadButton, 5, SpringLayout.WEST, this);
        mainLayout.putConstraint(SpringLayout.EAST, loadButton, -5, SpringLayout.WEST, itemList);

        mainLayout.putConstraint(SpringLayout.NORTH, removeButton, 5, SpringLayout.SOUTH, loadButton);
        mainLayout.putConstraint(SpringLayout.WEST, removeButton, 5, SpringLayout.WEST, this);

        mainLayout.putConstraint(SpringLayout.NORTH, clearButton, 5, SpringLayout.SOUTH, removeButton);
        mainLayout.putConstraint(SpringLayout.WEST, clearButton, 5, SpringLayout.WEST, this);
        mainLayout.putConstraint(SpringLayout.EAST, clearButton, -5, SpringLayout.WEST, itemList);

        //mainLayout.putConstraint(SpringLayout.NORTH, itemTextField, 5, SpringLayout.SOUTH, itemList);
        mainLayout.putConstraint(SpringLayout.SOUTH, itemTextField, -5, SpringLayout.SOUTH, this);
        mainLayout.putConstraint(SpringLayout.EAST, itemTextField, -5, SpringLayout.EAST, this);
        mainLayout.putConstraint(SpringLayout.WEST, itemTextField, 5, SpringLayout.EAST, addButton);

        mainLayout.putConstraint(SpringLayout.WEST, addButton, 5, SpringLayout.WEST, this);
        mainLayout.putConstraint(SpringLayout.EAST, addButton, -5, SpringLayout.WEST, itemList);
        mainLayout.putConstraint(SpringLayout.SOUTH, addButton, -5, SpringLayout.SOUTH, this);

        add(itemList);
        add(loadButton);
        add(removeButton);
        add(clearButton);
        add(itemTextField);
        add(addButton);
        setLayout(mainLayout);
    }
}
