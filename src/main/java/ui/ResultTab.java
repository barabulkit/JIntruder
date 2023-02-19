package ui;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(table),
                new JScrollPane(responseTextArea));

       table.addMouseListener(new MouseListener() {
           @Override
           public void mouseClicked(MouseEvent mouseEvent) {
               int index = table.getSelectedRow();
               String response = (String) tableModel.getValueAt(index, tableModel.getColumnCount()-1);
               Document doc = Jsoup.parse(response);
               responseTextArea.setText(doc.toString());
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

        add(splitPane);
        setLayout(mainLayout);
    }

    public void configureTable(Integer itemCount) {
        tableModel.setColumnCount(0);
        tableModel.addColumn("Request");

        for(int i = 0; i < itemCount; i++) {
            tableModel.addColumn(Integer.toString(i+1));
        }
        tableModel.addColumn("Response length");
        tableModel.addColumn("Response");
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
