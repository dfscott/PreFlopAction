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
    private int numberOfCallers;
    private int numberOfRaisers;
    private int amountToCall;
    private boolean isOpponentAllIn;

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

    public int getAnte() {
        return blinds.getAnte();

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

    public int getNumberOfCallers() {
        return numberOfCallers;
    }

    public void setNumberOfCallers(int numberOfCallers) {
        this.numberOfCallers = numberOfCallers;
    }

    public int getNumberOfRaisers() {
        return numberOfRaisers;
    }

    public void setNumberOfRaisers(int numberOfRaisers) {
        this.numberOfRaisers = numberOfRaisers;
    }

    public int getAmountToCall() {
        return amountToCall;
    }

    public void setAmountToCall(int amountToCall) {
        this.amountToCall = amountToCall;
    }

    public boolean isOpponentAllIn() {
        return isOpponentAllIn;
    }

    public void setOpponentAllIn(boolean opponentAllIn) {
        this.isOpponentAllIn = opponentAllIn;
    }

    public int getShortStackAmount() {

        int shortStack = getTotalChips();
        for (Player player : players) {
            if (player.getChipCount() < shortStack) {
                shortStack = player.getChipCount();
            }
        }
        return shortStack;
    }

    public Player getHero() {
        return players.get(heroPosition-1);
    }

    public boolean isHeroLatePosition() {
        Player hero = getHero();
        return (hero.getPosition() > remainingPlayers - 4);
    }

    public boolean isHeroBB() {
        Player hero = getHero();
        return (hero.getPosition() == remainingPlayers);
    }
}
