package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class PayloadTab extends JPanel {

    private final JComboBox payloadSetCombobox;
    private final ArrayList<SimpleListOptions> options;
    private SimpleListOptions currentOptionsPanel;

    public PayloadTab() {
        options = new ArrayList<SimpleListOptions>();
        JPanel payloadSetPanel = new JPanel();
        BoxLayout payloadSetLayout = new BoxLayout(payloadSetPanel, BoxLayout.X_AXIS);
        JLabel payloadSetLabel = new JLabel("Payload set");
        payloadSetCombobox = new JComboBox(new Integer[]{1});
        payloadSetCombobox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                Integer item = (Integer) itemEvent.getItem();
                remove(currentOptionsPanel);
                currentOptionsPanel = options.get(item-1);
                add(currentOptionsPanel);
                repaint();
            }
        });

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
        currentOptionsPanel = new SimpleListOptions();
        options.add(currentOptionsPanel);
        add(currentOptionsPanel);
        setLayout(mainLayout);
    }

    public void addSet(Integer num) {
        payloadSetCombobox.addItem(num);
        options.add(new SimpleListOptions());
    }

    public int getItemsCount() {
        return payloadSetCombobox.getItemCount();
    }

    public ArrayList<DefaultListModel> gatherPayloads() {
        ArrayList<DefaultListModel> result = new ArrayList<DefaultListModel>();

        for(SimpleListOptions opts : options) {
            result.add(opts.getListModel());
        }

        return result;
    }
}
