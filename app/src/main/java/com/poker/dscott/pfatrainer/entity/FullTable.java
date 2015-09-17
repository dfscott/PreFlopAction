package com.poker.dscott.pfatrainer.entity;

/**
 * Created by dscott on 9/13/2015.
 */
public class FullTable extends Table {

    private static final int STARTING_STACK = 1500;
    private static final int TOTAL_CHIPS = 15000;
    private static final int BUBBLE_PLAYER_COUNT = 4;
    private static final int MAX_PLAYERS = 10;
    private static final int MIN_PLAYERS = 3;

    public int getStartingStack() {
        return STARTING_STACK;
    }

    public int getTotalChips() {
        return TOTAL_CHIPS;
    }

    @Override
    public int getBubblePlayerCount() {
        return BUBBLE_PLAYER_COUNT;
    }

    @Override
    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    @Override
    public int getMinPlayers() {
        return MIN_PLAYERS;
    }

}
