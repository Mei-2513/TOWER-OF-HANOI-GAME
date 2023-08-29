package view;



import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.HanoiModel;



public class HanoiView extends JFrame {
    private JComboBox<String> levelComboBox;
    private JButton startButton;
    private JTextArea pegsTextArea;

    public HanoiView() {
        setTitle("Torre de Hanoi");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new FlowLayout());

        levelComboBox = new JComboBox<>(new String[]{"Principiantes", "Novato", "Intermedio", "Avanzado", "Experto", "Maestro"});
        controlsPanel.add(new JLabel("Selecciona la dificultad:"));
        controlsPanel.add(levelComboBox);

        startButton = new JButton("Iniciar Juego");
        controlsPanel.add(startButton);

        panel.add(controlsPanel, BorderLayout.NORTH);

        pegsTextArea = new JTextArea();
        pegsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(pegsTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        setLocationRelativeTo(null);
    }

    public String getSelectedLevel() {
        return (String) levelComboBox.getSelectedItem();
    }

    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
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
