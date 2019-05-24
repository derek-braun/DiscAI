package com.example.discnfc;

import java.util.LinkedList;

public class HoleData {
    private LinkedList<ThrowData> holeSet;
    private int score;

    public HoleData(LinkedList<ThrowData> holeSet, int score){
        this.holeSet = holeSet;
        this.score = score;
    }

    public int getScore(){
        return score;
    }
}
