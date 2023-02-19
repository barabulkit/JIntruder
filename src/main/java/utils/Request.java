package utils;

import java.util.ArrayList;

public class Request {

    private String request;
    private final ArrayList<String> insertions;

    public Request() {
        insertions = new ArrayList<>();
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public ArrayList<String> getInsertions() {
        return insertions;
    }

    public void addInsertion(String insertion) {
        insertions.add(insertion);
    }

    public Request duplicate() {
        Request request = new Request();
        request.setRequest(this.request);

        for(String insertion : insertions) {
            request.addInsertion(insertion);
        }

        return request;
    }
}
