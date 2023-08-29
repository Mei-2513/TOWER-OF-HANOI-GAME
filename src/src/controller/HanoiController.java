package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import model.HanoiModel;
import view.HanoiView;

public class HanoiController {
    private HanoiModel model;

    public HanoiController(HanoiModel model) {
        this.model = model;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        HanoiView.displayMessage("Bienvenido al juego de la Torre de Hanoi!");
        HanoiView.displayMessage("Elige el nivel de dificultad: Principiantes, Novato, Intermedio, Avanzado, Experto o Maestro");

        String level = scanner.nextLine();

        int diskCount = readDiskCountFromFile("src/src/input/niveles.txt", level);
        if (diskCount == -1) {
            HanoiView.displayMessage("Nivel no válido.");
            return;
        }

        model.initialize(diskCount);

        HanoiView.displayMessage("Comienza el juego:");
        HanoiView.displayPegs(model);

        solveHanoi(diskCount, 'A', 'C', 'B');

        HanoiView.displayMessage("¡Has ganado!");
    }

    private void solveHanoi(int n, char source, char dest, char aux) {
        if (n > 0) {
            solveHanoi(n - 1, source, aux, dest);
            model.moveDisk(source, dest);
            HanoiView.displayPegs(model);
            solveHanoi(n - 1, aux, dest, source);
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

    public static void main(String[] args) {
        HanoiModel model = new HanoiModel();
        HanoiController controller = new HanoiController(model);
        controller.play();
    }
}

