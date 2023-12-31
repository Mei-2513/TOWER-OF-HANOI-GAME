package controller;

import model.HanoiModel;
import view.HanoiView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class HanoiController {
    private HanoiModel model;
    private HanoiView view;
    private boolean gameStarted;
    private int diskCount;

    public HanoiController(HanoiModel model, HanoiView view) {
        this.model = model;
        this.view = view;
        gameStarted = false;
        diskCount = 0;

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

            if (moved && isWinConditionMet()) {
                int response = JOptionPane.showConfirmDialog(view, "¡Has ganado! ¿Quieres volver a intentarlo?", "Victoria", JOptionPane.YES_NO_OPTION);

                if (response == JOptionPane.YES_OPTION) {
                    restartGame(); // Llama a un nuevo método para reiniciar el juego
                } else if (response == JOptionPane.NO_OPTION) {
                    view.displayMessage("¡Hasta luego!");
                    view.dispose(); // Cerrar la aplicación
                }
            } else if (!moved) {
                view.displayMessage("Movimiento no válido.");
            }
        }
    }

    // Agrega este nuevo método para reiniciar el juego
    private void restartGame() {
        int response = JOptionPane.showConfirmDialog(view, "Debes seleccionar un nivel nuevamente para iniciar el juego.", "Reiniciar Juego", JOptionPane.OK_CANCEL_OPTION);

        if (response == JOptionPane.OK_OPTION) {
            String selectedLevel = view.getSelectedLevel();
            diskCount = readDiskCountFromFile("src/input/niveles.txt", selectedLevel);
            model.initialize(diskCount);
            view.clearPegs();
            updateView();
            gameStarted = true; // Re-iniciar el juego
        }
    }

    private boolean isWinConditionMet() {
        Stack<Integer> pegA = model.getPeg('A');
        Stack<Integer> pegB = model.getPeg('B');
        Stack<Integer> pegC = model.getPeg('C');

        // Comprueba si todas las barras excepto una están vacías
        if ((pegA.isEmpty() && pegB.isEmpty()) || (pegA.isEmpty() && pegC.isEmpty()) || (pegB.isEmpty() && pegC.isEmpty())) {
            Stack<Integer> nonEmptyPeg = pegA.isEmpty() ? (pegB.isEmpty() ? pegC : pegB) : pegA;

            int prevDisk = Integer.MAX_VALUE;
            Stack<Integer> tempStack = new Stack<>();

            // Verifica orden y almacena temporalmente en otra pila
            while (!nonEmptyPeg.isEmpty()) {
                int disk = nonEmptyPeg.pop();
                tempStack.push(disk);

                if (disk > prevDisk) {
                    return false;
                }
                prevDisk = disk;
            }

            // Restaura los discos a su pila original
            while (!tempStack.isEmpty()) {
                nonEmptyPeg.push(tempStack.pop());
            }
            return true;
        }

        return false;
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
