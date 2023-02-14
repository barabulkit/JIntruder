package ui;

import utils.AttackPerformer;
import utils.Position;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AllTabsPanel extends JTabbedPane {
    private final RequestTab requestTab;
    private final PayloadTab payloadTab;

    public AllTabsPanel() {
        requestTab = new RequestTab();
        payloadTab = new PayloadTab();

        requestTab.addStartButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String request = requestTab.getRequestString();
                ArrayList<Position> positions = requestTab.getPositions();
                String attackType = requestTab.getAttackType();
                ArrayList<DefaultListModel> options  = payloadTab.gatherPayloads();

                AttackPerformer attackPerformer = new AttackPerformer(request, positions, attackType, options);
                attackPerformer.performSniperAttack();
            }
        });

        requestTab.addAddButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int length = requestTab.getPositions().size();
                if(length + 1 == payloadTab.getItemsCount()) return;
                payloadTab.addSet(length+1);
            }
        });

        addTab("Positions", requestTab);
        addTab("Payloads", payloadTab);
    }

    public void performAttack() {

    }
}
