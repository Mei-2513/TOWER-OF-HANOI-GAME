package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;


public class HanoiView extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JComboBox<String> levelComboBox;
    private JButton startButton;
    private JButton moveButton;
    private JList<String> sourceList;
    private JList<String> destList;
    private JTextArea pegsTextArea;
    private JLabel groupLabel;

    public HanoiView() {
        setTitle("Torre de Hanoi");
        setSize(400, 400);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(4, 4));

        levelComboBox = new JComboBox<>(new String[]{"Principiantes", "Novato", "Intermedio", "Avanzado", "Experto", "Maestro"});
        controlsPanel.add(new JLabel("Selecciona la dificultad:"));
        controlsPanel.add(levelComboBox);

        sourceList = new JList<>(new String[]{"A", "B", "C"});
        sourceList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        controlsPanel.add(new JLabel("Origen:"));
        controlsPanel.add(new JScrollPane(sourceList));

        destList = new JList<>(new String[]{"A", "B", "C"});
        controlsPanel.add(new JLabel("Destino:"));
        controlsPanel.add(new JScrollPane(destList));

        moveButton = new JButton("Mover Barras");
        controlsPanel.add(moveButton);

        panel.add(controlsPanel, BorderLayout.NORTH);

        pegsTextArea = new JTextArea();
        pegsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(pegsTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());


        startButton = new JButton("Iniciar Juego");
        bottomPanel.add(startButton, BorderLayout.CENTER);

        groupLabel = new JLabel("Grupo: Silvia Juliana Rodriguez Rodriguez (202023822)"
                + "\r\n"
                + "Juan David López Castro (202023451) ");
        bottomPanel.add(groupLabel, BorderLayout.SOUTH);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
        setLocationRelativeTo(null);
    }
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void addMoveButtonListener(ActionListener listener) {
        moveButton.addActionListener(listener);
    }

    public String getSelectedLevel() {
        return (String) levelComboBox.getSelectedItem();
    }

    public String[] getSelectedSourcePegs() {
        return sourceList.getSelectedValuesList().toArray(new String[0]);
    }



    public String[] getSelectedDestPegs() {
        return destList.getSelectedValuesList().toArray(new String[0]);
    }
    public void setGroupInfo(String info) {
        groupLabel.setText(info);
    }



    public void displayPegs(Stack<Integer> pegA, Stack<Integer> pegB, Stack<Integer> pegC) {
        pegsTextArea.setText("A: " + pegA + "\nB: " + pegB + "\nC: " + pegC);
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void clearPegs() {
        pegsTextArea.setText("");
    }
}