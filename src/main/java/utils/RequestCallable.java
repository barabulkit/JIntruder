package utils;

import rawhttp.core.RawHttp;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.RawHttpResponse;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.zip.*;

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
        //Socket clientSocket = null;
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        //SSLSocket socket = null
        try {
            SSLSocket socket = (SSLSocket) factory.createSocket(InetAddress.getByName(target), 443);
            socket.startHandshake();

            RawHttp rawHttp = new RawHttp();

            RawHttpRequest httpRequest = rawHttp.parseRequest(request);

            httpRequest.writeTo(socket.getOutputStream());

            RawHttpResponse<?> response = rawHttp.parseResponse(socket.getInputStream());

            InputStream is = response.eagerly().getBody().get().asRawStream();
            Scanner scanner;
            System.out.println(httpRequest.getHeaders().getHeaderNames());
            if(httpRequest.getHeaders().get("Accept-Encoding", ",").contains("gzip")) {
                System.out.println("gzip");
                GZIPInputStream gis = new GZIPInputStream(is);
                scanner = new Scanner(gis);
            } else {
                scanner = new Scanner(is);
            }
            StringBuilder res = new StringBuilder();
            while(scanner.hasNextLine()) {
                res.append(scanner.nextLine());
            }
            callback.setResponse(res.toString());
            callback.callbackMethod();
            socket.close();
            return "";
            //return result.toString();
        } catch (IOException e) {
            callback.setResponse(e.getMessage());
            callback.callbackMethod();
            e.printStackTrace();
        }
        return "no response";
    }

    private boolean isCompressed(final byte[] compressed) {
        return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC)) && (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
    }
}
