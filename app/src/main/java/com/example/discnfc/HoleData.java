package com.example.discnfc;

import java.io.Serializable;
import java.util.LinkedList;

public class HoleData implements Serializable {
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
