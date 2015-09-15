package com.poker.dscott.pfatrainer.entity;

import java.util.List;

/**
 * Created by dscott on 9/13/2015.
 */
public abstract class Table {

    public final static int minimumPlayerChips = 100;

    private int numberOfPlayers;
    private int BB;
    private int SB;
    private Hand heroHand;
    private int heroPosition;
    private List<Player> players;

    public abstract int getStartingStack();

    public abstract int getTotalChips();

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getBB() {
        return BB;
    }

    public void setBB(int BB) {
        this.BB = BB;
    }

    public int getSB() {
        return SB;
    }

    public void setSB(int SB) {
        this.SB = SB;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Hand getHeroHand() {
        return heroHand;
    }

    public void setHeroHand(Hand heroHand) {
        this.heroHand = heroHand;
    }

    public int getHeroPosition() {
        return heroPosition;
    }

    public void setHeroPosition(int heroPosition) {
        this.heroPosition = heroPosition;
    }
}
