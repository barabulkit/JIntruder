package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabComponent extends JPanel {
    public TabComponent(String title, final CustomTabbedPane parent) {
        JLabel label = new JLabel(title);
        JButton button = new JButton("X");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int index = parent.indexOfTabComponent(TabComponent.this);
                parent.setSelectedIndex(0);
                parent.getTabs().remove(index);
                parent.remove(index);
            }
        });

        add(label);
        add(button);
    }
}
