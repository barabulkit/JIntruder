package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ResultTab extends JPanel {

    private final DefaultTableModel tableModel;

    public ResultTab() {
        BoxLayout mainLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        JTextArea responseTextArea = new JTextArea();

       table.addMouseListener(new MouseListener() {
           @Override
           public void mouseClicked(MouseEvent mouseEvent) {
               int index = table.getSelectedRow();
               String response = (String) tableModel.getValueAt(index, 2);
               responseTextArea.setText(response);
           }

           @Override
           public void mousePressed(MouseEvent mouseEvent) {

           }

           @Override
           public void mouseReleased(MouseEvent mouseEvent) {

           }

           @Override
           public void mouseEntered(MouseEvent mouseEvent) {

           }

           @Override
           public void mouseExited(MouseEvent mouseEvent) {

           }
       });

        add(new JScrollPane(table));
        add(new JScrollPane(responseTextArea));
        setLayout(mainLayout);
    }

    public void configureTable() {
        tableModel.addColumn("Request");
        tableModel.addColumn("Response length");
        tableModel.addColumn("Response");
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
