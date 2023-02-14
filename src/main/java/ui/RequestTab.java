package ui;

import utils.Position;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RequestTab extends JPanel {

    private final DefaultHighlighter highlighter;
    private final Highlighter.HighlightPainter painter;
    private final ArrayList<Position> positions;

    public RequestTab() {
        positions = new ArrayList<Position>();
        BoxLayout mainLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

        JPanel attackTypePanel = new JPanel();
        BoxLayout attackTypeLayout = new BoxLayout(attackTypePanel, BoxLayout.X_AXIS);

        JLabel attackLabel = new JLabel("attack type: ");
        JComboBox attackTypeCombobox = new JComboBox(new String[]{"Sniper", "Cluster bomb", "Pitchfork"});
        JButton startButton = new JButton("Start");

        attackTypePanel.add(attackLabel);
        attackTypePanel.add(attackTypeCombobox);
        attackTypePanel.add(Box.createRigidArea(new Dimension(10,0)));
        attackTypePanel.add(startButton);
        attackTypePanel.setLayout(attackTypeLayout);

        JButton addPositionButton = new JButton("Add $");
        JButton removePositionButton = new JButton("Remove $");
        JButton clearButton = new JButton("Clear $");
        final JTextArea requestTextArea = new JTextArea();
        JPanel requestPanel = new JPanel();

        highlighter = (DefaultHighlighter) requestTextArea.getHighlighter();
        highlighter.setDrawsLayeredHighlights(false);
        painter = new DefaultHighlighter.DefaultHighlightPainter(Color.MAGENTA);

        addPositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int startIndex = requestTextArea.getSelectionStart();
                int endIndex = requestTextArea.getSelectionEnd();

                String text = new StringBuffer(requestTextArea.getText()).insert(startIndex, '$')
                        .insert(endIndex+1, '$').toString();
                requestTextArea.setText(text);

                positions.add(new Position(startIndex, endIndex+2));

                highlight();
            }
        });

        removePositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int startIndex = requestTextArea.getSelectionStart();
                int endIndex = requestTextArea.getSelectionEnd();

                for(Highlighter.Highlight x : highlighter.getHighlights()) {
                    if(x.getStartOffset() == startIndex && x.getEndOffset() == endIndex) {
                        highlighter.removeHighlight(x);
                    }
                }

                for(Position x : positions) {
                    if(x.getStartIndex() == startIndex && x.getEndIndex() == endIndex) {
                        positions.remove(x);
                    }
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                highlighter.removeAllHighlights();
                positions.clear();
            }
        });

        SpringLayout springLayout = new SpringLayout();
        springLayout.putConstraint(SpringLayout.NORTH, addPositionButton, 5, SpringLayout.NORTH, requestPanel);
        springLayout.putConstraint(SpringLayout.EAST, addPositionButton, -5, SpringLayout.EAST, requestPanel);
        springLayout.putConstraint(SpringLayout.WEST, addPositionButton, 5, SpringLayout.EAST, requestTextArea);

        springLayout.putConstraint(SpringLayout.NORTH, removePositionButton, 5, SpringLayout.SOUTH, addPositionButton);
        springLayout.putConstraint(SpringLayout.EAST, removePositionButton, -5, SpringLayout.EAST, requestPanel);

        springLayout.putConstraint(SpringLayout.NORTH, clearButton, 5, SpringLayout.SOUTH, removePositionButton);
        springLayout.putConstraint(SpringLayout.EAST, clearButton, -5, SpringLayout.EAST, requestPanel);
        springLayout.putConstraint(SpringLayout.WEST, clearButton, 5, SpringLayout.EAST, requestTextArea);

        springLayout.putConstraint(SpringLayout.NORTH, requestTextArea, 5, SpringLayout.NORTH, requestPanel);
        springLayout.putConstraint(SpringLayout.WEST, requestTextArea, 0, SpringLayout.WEST, requestPanel);
        springLayout.putConstraint(SpringLayout.SOUTH, requestTextArea, 0, SpringLayout.SOUTH, requestPanel);
        springLayout.putConstraint(SpringLayout.EAST, requestTextArea, -5, SpringLayout.WEST, addPositionButton);
        springLayout.putConstraint(SpringLayout.EAST, requestTextArea, -5, SpringLayout.WEST, removePositionButton);


        requestPanel.add(requestTextArea);
        requestPanel.add(addPositionButton);
        requestPanel.add(clearButton);
        requestPanel.add(removePositionButton);
        requestPanel.setLayout(springLayout);


        this.add(attackTypePanel);
        this.add(requestPanel);
        this.setLayout(mainLayout);
    }

    private void highlight() {
        try {
            for(Position x : positions) {
                highlighter.addHighlight(x.getStartIndex(), x.getEndIndex(), painter);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
