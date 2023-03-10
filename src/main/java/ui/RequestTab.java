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
    private final JTextArea requestTextArea;
    private final JButton startButton;
    private final JButton addPositionButton;
    private final JComboBox attackTypeCombobox;
    private final JTextField targetTextField;

    public RequestTab() {
        positions = new ArrayList<Position>();
        BoxLayout mainLayout = new BoxLayout(this, BoxLayout.Y_AXIS);

        JPanel attackTypePanel = new JPanel();
        BoxLayout attackTypeLayout = new BoxLayout(attackTypePanel, BoxLayout.X_AXIS);

        JLabel attackLabel = new JLabel("attack type: ");
        attackTypeCombobox = new JComboBox(new String[]{"Sniper", "Cluster bomb", "Pitchfork"});
        startButton = new JButton("Start");

        attackTypePanel.add(Box.createRigidArea(new Dimension(5, 0)));
        attackTypePanel.add(attackLabel);
        attackTypePanel.add(attackTypeCombobox);
        attackTypePanel.add(Box.createRigidArea(new Dimension(5,0)));
        attackTypePanel.add(startButton);
        attackTypePanel.add(Box.createRigidArea(new Dimension(5,0)));
        attackTypePanel.setLayout(attackTypeLayout);

        JPanel targetPanel = new JPanel();
        BoxLayout targetLayout = new BoxLayout(targetPanel, BoxLayout.X_AXIS);
        JLabel targetLabel = new JLabel("target: ");
        targetTextField = new JTextField();

        targetPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        targetPanel.add(targetLabel);
        targetPanel.add(targetTextField);
        targetPanel.add(Box.createRigidArea(new Dimension(5,0)));
        targetPanel.setLayout(targetLayout);
        targetPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));

        addPositionButton = new JButton("Add $");
        JButton removePositionButton = new JButton("Remove $");
        JButton clearButton = new JButton("Clear $");
        requestTextArea = new JTextArea();
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
                calculatePositions(text);
                int tmp = requestTextArea.getCaretPosition();
                requestTextArea.setText(text);
                requestTextArea.setCaretPosition(tmp);

                highlight();
            }
        });

        removePositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int startIndex = requestTextArea.getSelectionStart();
                int endIndex = requestTextArea.getSelectionEnd();

                StringBuilder editor = new StringBuilder(requestTextArea.getText());
                editor.replace(startIndex, startIndex+1, "");
                editor.replace(endIndex-2, endIndex-1, "");
                int tmp = requestTextArea.getCaretPosition();
                requestTextArea.setText(editor.toString());
                requestTextArea.setCaretPosition(tmp);

                calculatePositions(editor.toString());
                highlight();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                highlighter.removeAllHighlights();
                int tmp = requestTextArea.getCaretPosition();
                requestTextArea.setText(requestTextArea.getText().replaceAll("\\$", ""));
                requestTextArea.setCaretPosition(tmp);
                positions.clear();
            }
        });

        JScrollPane textAreaPane = new JScrollPane(requestTextArea);

        SpringLayout springLayout = new SpringLayout();
        springLayout.putConstraint(SpringLayout.NORTH, addPositionButton, 5, SpringLayout.NORTH, requestPanel);
        springLayout.putConstraint(SpringLayout.EAST, addPositionButton, -5, SpringLayout.EAST, requestPanel);
        springLayout.putConstraint(SpringLayout.WEST, addPositionButton, 5, SpringLayout.EAST, textAreaPane);

        springLayout.putConstraint(SpringLayout.NORTH, removePositionButton, 5, SpringLayout.SOUTH, addPositionButton);
        springLayout.putConstraint(SpringLayout.EAST, removePositionButton, -5, SpringLayout.EAST, requestPanel);

        springLayout.putConstraint(SpringLayout.NORTH, clearButton, 5, SpringLayout.SOUTH, removePositionButton);
        springLayout.putConstraint(SpringLayout.EAST, clearButton, -5, SpringLayout.EAST, requestPanel);
        springLayout.putConstraint(SpringLayout.WEST, clearButton, 5, SpringLayout.EAST, textAreaPane);

        springLayout.putConstraint(SpringLayout.NORTH, textAreaPane, 5, SpringLayout.NORTH, requestPanel);
        springLayout.putConstraint(SpringLayout.WEST, textAreaPane, 0, SpringLayout.WEST, requestPanel);
        springLayout.putConstraint(SpringLayout.SOUTH, textAreaPane, 0, SpringLayout.SOUTH, requestPanel);
        springLayout.putConstraint(SpringLayout.EAST, textAreaPane, -5, SpringLayout.WEST, addPositionButton);
        springLayout.putConstraint(SpringLayout.EAST, textAreaPane, -5, SpringLayout.WEST, removePositionButton);


        requestPanel.add(textAreaPane);
        requestPanel.add(addPositionButton);
        requestPanel.add(clearButton);
        requestPanel.add(removePositionButton);
        requestPanel.setLayout(springLayout);

        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(attackTypePanel);
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        this.add(targetPanel);
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

    public ArrayList<Position> getPositions() {
        return positions;
    }

    private void calculatePositions(String text) {
        positions.clear();
        int offset = 0;
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        while(text.indexOf('$') >= 0) {
            indexes.add(text.indexOf('$') + offset);
            int adder = text.indexOf('$')+1;
            text = text.substring(text.indexOf('$')+1);
            offset += adder;
        }
        if(indexes.size() % 2 != 0) return;
        for(int i = 0; i < indexes.size(); i += 2) {
            positions.add(new Position(indexes.get(i), indexes.get(i+1)+1));
        }
    }

    public String getRequestString() {
        return requestTextArea.getText();
    }

    public void addStartButtonActionListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void addAddButtonActionListener(ActionListener listener) {
        addPositionButton.addActionListener(listener);
    }

    public String getAttackType() {
        return (String) attackTypeCombobox.getSelectedItem();
    }

    public String getTarget() {
        return targetTextField.getText();
    }
}
