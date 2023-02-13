package ui;

import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        super("JIntruder");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        CustomTabbedPane tabbedPane = new CustomTabbedPane();

        add(tabbedPane);

        setSize(800,600);
        setVisible(true);
    }
}
