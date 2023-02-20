package utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AttackPerformer {

    private final String target;
    private final String request;
    private final ArrayList<Position> positions;
    private final String attackType;
    private final ArrayList<DefaultListModel> options;
    private final ExecutorService executor;
    private final DefaultTableModel tableModel;

    public AttackPerformer(String target, String request, ArrayList<Position> positions, String attackType,
                           ArrayList<DefaultListModel> options, DefaultTableModel tableModel) {
        this.target = target;
        this.request = request;
        this.positions = positions;
        this.attackType = attackType;
        this.options = options;
        this.tableModel = tableModel;

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
            Request dup = new Request();
            dup.setRequest(requestEditor.toString());
            dup.addInsertion(payload);
            RequestCallback requestCallback = new RequestCallback(dup, tableModel);
            RequestCallable requestCallable = new RequestCallable(target, requestEditor.toString(), requestCallback);
            executor.submit(requestCallable);
        }

        return results;
    }

    public ArrayList<String> performClusterBombAttack() {
        ArrayList<String> result = new ArrayList<String>();

        if(options.size() < positions.size()) return result;
        Request dup = new Request();
        dup.setRequest(request);
        createRequests(dup, 0, positions, options, 0);

        return result;
    }

    public void createRequests(Request request, int positionNum, ArrayList<Position> positions, ArrayList<DefaultListModel> options, int offset) {
        Position position = positions.get(positionNum);
        for(int i = 0; i < options.get(positionNum).size(); i++) {
            Request dup = request.duplicate();
            StringBuilder editor = new StringBuilder(dup.getRequest());
            String replacer = (String) options.get(positionNum).getElementAt(i);
            int newOffset = offset - (position.getEndIndex() - position.getStartIndex()) + replacer.length();
            editor.replace(position.getStartIndex()+offset, position.getEndIndex()+offset, replacer);
            dup.setRequest(editor.toString());
            dup.addInsertion(replacer);
            if(positionNum != positions.size() - 1) {
                createRequests(dup, positionNum + 1, positions, options, newOffset);
            } else {
                RequestCallback requestCallback = new RequestCallback(dup, tableModel);
                RequestCallable requestCallable = new RequestCallable(target, editor.toString(), requestCallback);
                executor.submit(requestCallable);
            }
        }
    }

    public void performAttack() {
        if(attackType.equals("Sniper")) {
            performSniperAttack();
        } else if(attackType.equals("Cluster bomb")) {
            performClusterBombAttack();
        }
    }
}
