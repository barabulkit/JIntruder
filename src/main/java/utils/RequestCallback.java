package utils;

public class RequestCallback {

    private String response;

    public RequestCallback() {
        this.response = "empty response";
    }

    public void callbackMethod() {
        String[] lines = response.split("\r\n");
        for(String line : lines) {
            System.out.println(line);
        }
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
