package com.poker.dscott.pfatrainer.entity;

/**
 * Created by dscott on 9/15/2015.
 */
public class Blinds {

    private int bigBlindAmount;
    private int smallBlindAmount;
    private int ante;
    private int level;

    public int getBigBlindAmount() {
        return bigBlindAmount;
    }

    public void setBigBlindAmount(int bigBlindAmount) {
        this.bigBlindAmount = bigBlindAmount;
    }

    public int getSmallBlindAmount() {
        return smallBlindAmount;
    }

    public void setSmallBlindAmount(int smallBlindAmount) {
        this.smallBlindAmount = smallBlindAmount;
    }

    public int getAnte() {
        return ante;
    }

    public void setAnte(int ante) {
        this.ante = ante;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
