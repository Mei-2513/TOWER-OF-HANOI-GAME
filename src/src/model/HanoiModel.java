package model;

import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

public class HanoiModel {
    private Stack<Integer> pegA, pegB, pegC;
    private int diskCount;

    public HanoiModel() {
        pegA = new Stack<>();
        pegB = new Stack<>();
        pegC = new Stack<>();
        setDiskCount(0);
    }

    public void initialize(int diskCount) {
        this.setDiskCount(diskCount);
        List<Integer> disks = new ArrayList<>();

        // Agregar discos a la lista en orden ascendente
        for (int i = 1; i <= diskCount; i++) {
            disks.add(i);
        }

        // Mezclar la lista para distribuir los discos aleatoriamente
        Collections.shuffle(disks);

        pegA.clear();
        pegB.clear();
        pegC.clear();

        // Distribuir discos en las pilas
        for (int i = 0; i < diskCount; i++) {
            pegA.push(disks.get(i));
        }
    }

    public boolean moveDisk(char source, char dest) {
        Stack<Integer> sourcePeg = getPeg(source);
        Stack<Integer> destPeg = getPeg(dest);

        if (sourcePeg.isEmpty()) {
            return false;
        }

        int disk = sourcePeg.pop();
        destPeg.push(disk);
        return true;
    }


    public boolean isGameFinished() {
        return pegA.isEmpty() && pegB.isEmpty();
    }

    public Stack<Integer> getPeg(char peg) {
        if (peg == 'A') {
            return pegA;
        } else if (peg == 'B') {
            return pegB;
        } else if (peg == 'C') {
            return pegC;
        }
        return null;
    }

    public int getDiskCount() {
        return diskCount;
    }

    public void setDiskCount(int diskCount) {
        this.diskCount = diskCount;
    }
}
