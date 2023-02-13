package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class CustomTabbedPane extends JTabbedPane implements ChangeListener {

    private int tabCount = 1;
    private final ArrayList<RequestTab> tabs;

    CustomTabbedPane() {
        super(JTabbedPane.TOP);
        tabs = new ArrayList<RequestTab>();
        this.setTabPlacement(JTabbedPane.TOP);
        tabs.add(new RequestTab());
        this.addTab("1", tabs.get(0));
        this.setTabComponentAt(0, new TabComponent("1", this));
        this.addTab("+", null);
        this.addChangeListener(this);
    }

    public ArrayList<RequestTab> getTabs() {
        return this.tabs;
    }

    public void stateChanged(ChangeEvent changeEvent) {
        CustomTabbedPane tabbedPane = (CustomTabbedPane) changeEvent.getSource();
        int selectedIndex = tabbedPane.getSelectedIndex();
        String title = tabbedPane.getTitleAt(selectedIndex);
        if(title.equals("+")) {
            customAddTab(selectedIndex);
        }
    }

    private void customAddTab(int selectedIndex) {
        this.tabCount++;
        this.tabs.add(new RequestTab());
        this.setTitleAt(selectedIndex, Integer.toString(this.tabCount));
        this.setTabComponentAt(selectedIndex, new TabComponent(Integer.toString(this.tabCount), this));
        this.setComponentAt(selectedIndex, this.tabs.get(this.tabs.size()-1));
        this.addTab("+", null);
    }
}
