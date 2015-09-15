package com.poker.dscott.pfatrainer.entity;

import java.util.List;

/**
 * Created by dscott on 9/13/2015.
 */
public abstract class Table {

    public final static int minimumPlayerChips = 100;

    private int remainingPlayers;
    private Blinds blinds;
    private Hand heroHand;
    private int heroPosition;
    private List<Player> players;

    public abstract int getStartingStack();

    public abstract int getTotalChips();

    public abstract int getBubblePlayerCount();

    public abstract int getMaxPlayers();

    public abstract int getMinPlayers();

    public int getRemainingPlayers() {
        return remainingPlayers;
    }

    public void setRemainingPlayers(int remainingPlayers) {
        this.remainingPlayers = remainingPlayers;
    }

    public int getBB() {
        return blinds.getBigBlindAmount();
    }

    public int getSB() {
        return blinds.getSmallBlindAmount();
    }

    public Blinds getBlinds() {
        return blinds;
    }

    public void setBlinds(Blinds blinds) {
        this.blinds = blinds;
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
