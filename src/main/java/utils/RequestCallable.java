package utils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

public class RequestCallable implements Callable<String> {

    private final String target;
    private final String request;
    private final RequestCallback callback;

    RequestCallable(String target, String request, RequestCallback callback) {
        this.target = target;
        this.request = request;
        this.callback = callback;
    }

    @Override
    public String call() {
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(InetAddress.getByName(target), 80);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String[] lines = request.split("\r\n");
            for(String line : lines) {
                writer.write(line + "\r\n");
            }
            writer.write("\r\n");
            writer.flush();

            InputStream input = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }

            callback.setResponse(result.toString());
            callback.callbackMethod();
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "no response";
    }
}
