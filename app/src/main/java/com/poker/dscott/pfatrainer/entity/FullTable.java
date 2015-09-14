package com.poker.dscott.pfatrainer.entity;

/**
 * Created by dscott on 9/13/2015.
 */
public class FullTable extends Table {

    private static final int STARTING_STACK = 1500;
    private static final int TOTAL_CHIPS = 15000;

    public int getStartingStack() {
        return STARTING_STACK;
    }

    public int getTotalChips() {
        return TOTAL_CHIPS;
    }
}
