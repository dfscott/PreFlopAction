package com.poker.dscott.pfatrainer.entity;

/**
 * Created by dscott on 9/13/2015.
 */
public class Player {

    public enum Action {
        FOLD,
        CALL,
        RAISE
    }
    private int chipCount;
    private Hand hand;
    private int position;
    private Action action;
    private int bet;

    public int getChipCount() {
        return chipCount;
    }

    public void setChipCount(int chipCount) {
        this.chipCount = chipCount;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public boolean isAllIn() {
        boolean isAllin = false;
        if (getBet() > 0) {
            isAllin = getBet() == getChipCount();
        }
        return isAllin;
    }
}
