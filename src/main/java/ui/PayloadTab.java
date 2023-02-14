package ui;

import javax.swing.*;
import java.awt.*;

public class PayloadTab extends JPanel {
    public PayloadTab() {
        JPanel payloadSetPanel = new JPanel();
        BoxLayout payloadSetLayout = new BoxLayout(payloadSetPanel, BoxLayout.X_AXIS);
        JLabel payloadSetLabel = new JLabel("Payload set");
        JComboBox payloadSetCombobox = new JComboBox(new String[]{});

        payloadSetPanel.add(payloadSetLabel);
        payloadSetPanel.add(Box.createRigidArea(new Dimension(5,0)));
        payloadSetPanel.add(payloadSetCombobox);
        payloadSetPanel.setLayout(payloadSetLayout);

        JPanel payloadTypePanel = new JPanel();
        BoxLayout payloadTypeLayout = new BoxLayout(payloadTypePanel, BoxLayout.X_AXIS);
        JLabel payloadTypeLabel = new JLabel("Payload type");
        JComboBox payloadTypeCombobox = new JComboBox(new String[]{"Simple List", "Number"});

        payloadTypePanel.add(payloadTypeLabel);
        payloadTypePanel.add(Box.createRigidArea(new Dimension(5,0)));
        payloadTypePanel.add(payloadTypeCombobox);
        payloadTypePanel.setLayout(payloadTypeLayout);

        BoxLayout mainLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        add(payloadSetPanel);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(payloadTypePanel);
        add(new SimpleListOptions());
        setLayout(mainLayout);
    }
}
