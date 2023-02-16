package utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AttackPerformer {

    private final String target;
    private final String request;
    private final ArrayList<Position> positions;
    private final String attackType;
    private final ArrayList<DefaultListModel> options;
    private final ExecutorService executor;

    public AttackPerformer(String target, String request, ArrayList<Position> positions, String attackType,
                           ArrayList<DefaultListModel> options) {
        this.target = target;
        this.request = request;
        this.positions = positions;
        this.attackType = attackType;
        this.options = options;

        executor = Executors.newFixedThreadPool(10);
    }

    public ArrayList<String> performSniperAttack() {
        ArrayList<String> results = new ArrayList<String>();

        DefaultListModel firstPayload = options.get(0);
        for(int i = 0; i < firstPayload.size(); i++) {
            String payload = (String) firstPayload.getElementAt(i);
            StringBuffer requestEditor = new StringBuffer(request);
            int offset = 0;
            for(Position pos : positions) {
                int length = pos.getEndIndex() - pos.getStartIndex();
                requestEditor.delete(pos.getStartIndex() + offset, pos.getEndIndex() + offset);
                requestEditor.insert(pos.getStartIndex() + offset, payload);
                offset = offset - length + payload.length();
            }
            RequestCallback requestCallback = new RequestCallback();
            RequestCallable requestCallable = new RequestCallable(target, requestEditor.toString(), requestCallback);
            executor.submit(requestCallable);
            //doRequest("", requestEditor.toString());
            //System.out.println(requestEditor.toString());
        }

        return results;
    }

    public ArrayList<String> performClusterBombAttack() {
        ArrayList<String> result = new ArrayList<String>();

        if(options.size() < positions.size()) return result;
        printAll(request, 0, positions, options, 0);

        return result;
    }

    public void printAll(String request, int positionNum, ArrayList<Position> positions, ArrayList<DefaultListModel> options, int offset) {
        Position position = positions.get(positionNum);
        for(int i = 0; i < options.get(positionNum).size(); i++) {
            StringBuilder editor = new StringBuilder(request);
            String replacer = (String) options.get(positionNum).getElementAt(i);
            int newOffset = offset - (position.getEndIndex() - position.getStartIndex()) + replacer.length();
            editor.replace(position.getStartIndex()+offset, position.getEndIndex()+offset, replacer);
            if(positionNum != positions.size() - 1) {
                printAll(editor.toString(), positionNum + 1, positions, options, newOffset);
            } else {
                System.out.println(editor.toString());
            }
        }
    }

    public Future<String> doRequest(String target, String request) {
        return executor.submit(() -> {
            Socket clientSocket = new Socket(target, 80);
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            Scanner in = new Scanner(new InputStreamReader(clientSocket.getInputStream()));

            writer.println(request);
            String result = "";
            while(in.hasNextLine()) {
                result = result + in.nextLine();
            }

            return result;
        });
    }
}
