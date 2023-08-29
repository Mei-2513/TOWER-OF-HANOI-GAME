package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import model.HanoiModel;
import view.HanoiView;


public class HanoiController {
    private HanoiModel model;
    private HanoiView view;
    private boolean gameStarted;

    public HanoiController(HanoiModel model, HanoiView view) {
        this.model = model;
        this.view = view;
        gameStarted = false;

        view.addStartButtonListener(new StartButtonListener());
        view.addMoveButtonListener(new MoveButtonListener());
    }

    public void play() {
        view.setVisible(true);
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedLevel = view.getSelectedLevel();
            int diskCount = readDiskCountFromFile("src/src/input/niveles.txt", selectedLevel);

            if (diskCount == -1) {
                view.displayMessage("Nivel no válido.");
                return;
            }

            model.initialize(diskCount);
            view.clearPegs();
            updateView();
            gameStarted = true;
        }
    }

    private class MoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!gameStarted) {
                view.displayMessage("Debes iniciar el juego primero.");
                return;
            }

            String[] selectedSourcePegs = view.getSelectedSourcePegs();
            String[] selectedDestPegs = view.getSelectedDestPegs();

            boolean moved = false;
            for (String sourcePeg : selectedSourcePegs) {
                for (String destPeg : selectedDestPegs) {
                    moved = model.moveDisk(sourcePeg.charAt(0), destPeg.charAt(0));
                    if (moved) {
                        updateView();
                    }
                }
            }

            if (moved && model.isGameFinished()) {
                view.displayMessage("¡Has ganado!");
                gameStarted = false;
            } else if (!moved) {
                view.displayMessage("Movimiento no válido.");
            }
        }
    }

    private int readDiskCountFromFile(String filename, String targetLevel) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String level = parts[0].trim();
                    if (level.equalsIgnoreCase(targetLevel)) {
                        reader.close();
                        return Integer.parseInt(parts[1].trim());
                    }
                }
            }

            reader.close();
            return -1; // Nivel no encontrado
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void updateView() {
        Stack<Integer> pegA = model.getPeg('A');
        Stack<Integer> pegB = model.getPeg('B');
        Stack<Integer> pegC = model.getPeg('C');
        view.displayPegs(pegA, pegB, pegC);
    }

    public static void main(String[] args) {
        HanoiModel model = new HanoiModel();
        HanoiView view = new HanoiView();
        HanoiController controller = new HanoiController(model, view);
        controller.play();
    }
}

