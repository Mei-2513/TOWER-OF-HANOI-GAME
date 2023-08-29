package model;

import java.util.Stack;

import java.util.Stack;

import java.util.*;

import java.util.Stack;

public class HanoiModel {
    private Stack<Integer> pegA, pegB, pegC;
    private int diskCount;

    public HanoiModel() {
        pegA = new Stack<>();
        pegB = new Stack<>();
        pegC = new Stack<>();
        diskCount = 0;
    }

    public void initialize(int diskCount) {
        this.diskCount = diskCount;
        pegA.clear();
        pegB.clear();
        pegC.clear();
        for (int i = diskCount; i > 0; i--) {
            pegA.push(i);
        }
    }

    public boolean moveDisk(char source, char dest) {
        Stack<Integer> sourcePeg = getPeg(source);
        Stack<Integer> destPeg = getPeg(dest);

        if (sourcePeg.isEmpty() || (!destPeg.isEmpty() && sourcePeg.peek() > destPeg.peek())) {
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
}

