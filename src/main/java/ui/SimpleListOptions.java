package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SimpleListOptions  extends JPanel {

    private final DefaultListModel listModel;
    private final JFileChooser fileChooser;

    public SimpleListOptions() {
        SpringLayout mainLayout = new SpringLayout();

        listModel = new DefaultListModel();
        fileChooser = new JFileChooser();

        final JList itemList = new JList();
        JButton loadButton = new JButton("Load");
        JButton removeButton = new JButton("Remove");
        JButton clearButton = new JButton("Clear");
        JButton addButton = new JButton("Add");
        final JTextField itemTextField = new JTextField();

        itemList.setModel(listModel);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String text = itemTextField.getText();
                if(text.isEmpty()) return;

                listModel.addElement(text);
                itemTextField.setText("");
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listModel.remove(itemList.getSelectedIndex());
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listModel.clear();
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int res = fileChooser.showOpenDialog(SimpleListOptions.this);
                if(res == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        Scanner scanner = new Scanner(file);
                        while(scanner.hasNextLine()) {
                            listModel.addElement(scanner.nextLine());
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
