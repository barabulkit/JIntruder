package utils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AttackPerformer {

    private final String request;
    private final ArrayList<Position> positions;
    private final String attackType;
    private final ArrayList<DefaultListModel> options;
    private final ExecutorService executor;

    public AttackPerformer(String request, ArrayList<Position> positions, String attackType,
                           ArrayList<DefaultListModel> options) {
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
            System.out.println(requestEditor.toString());
        }

        return results;
    }

    public ArrayList<String> performClusterBombAttack() {
        ArrayList<String> result = new ArrayList<String>();

        if(options.size() < positions.size()) return result;
        ArrayList<String> list = new ArrayList<String>();
        list.add(request);
        for(int i = 0; i < positions.size(); i++) {
            list = fillPosition(list, positions.get(i), options.get(i));
        }

        System.out.println(list.size());
        return result;
    }

    public ArrayList<String> fillPosition(ArrayList<String> requests, Position position, DefaultListModel options) {
        ArrayList<String> result = new ArrayList<String>();
        for(String request : requests) {
            for(int i = 0; i < options.size(); i++) {
                StringBuffer buffer = new StringBuffer(request);
                buffer.replace(position.getStartIndex(), position.getEndIndex(), (String) options.getElementAt(i));
                result.add(buffer.toString());
            }
        }
        return result;
    }
}
