package com.poker.dscott.pfatrainer.entity;

/**
 * Created by dscott on 9/13/2015.
 */
public class Player {

    public enum Action {
        CHECK("Check"),
        FOLD("Fold"),
        CALL("Call"),
        RAISE("Raise"),
        ALLIN("Go All-in"),
        NO_ACTION("");

        private String text;

        Action(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
    private int chipCount;
    private Hand hand;
    private int position;
    private Action action;
    private int bet;

    public Player() {
        action = Action.NO_ACTION;
    }
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
