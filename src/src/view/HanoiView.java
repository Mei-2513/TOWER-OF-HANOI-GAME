package view;

import model.HanoiModel;




public class HanoiView {
    public static void displayPegs(HanoiModel model) {
        System.out.println("A: " + model.getPeg('A'));
        System.out.println("B: " + model.getPeg('B'));
        System.out.println("C: " + model.getPeg('C'));
    }

    public static void displayMessage(String message) {
        System.out.println(message);
    }
}