package utils;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class RequestCallback {

    private final Request request;
    private String response;
    private final DefaultTableModel tableModel;

    public RequestCallback(Request request, DefaultTableModel tableModel) {
        this.request = request;
        this.tableModel = tableModel;
        this.response = "empty response";
    }

    public void callbackMethod() {
        String[] lines = response.split("\r\n");
        Vector<String> row = new Vector<>();
        row.add(request.getRequest());
        System.out.println();
        row.addAll(request.getInsertions());
        row.add(Integer.toString(response.length()));
        row.add(response);
        tableModel.addRow(row);

        /*for(String line : lines) {
            System.out.println(line);
        }*/
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
