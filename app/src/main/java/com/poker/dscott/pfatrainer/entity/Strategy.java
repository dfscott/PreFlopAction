package com.poker.dscott.pfatrainer.entity;

/**
 * Created by dscott on 9/15/2015.
 */
public abstract class Strategy {

    private Table table;
    private Hand playerHand;

    public abstract String getStrategyName();

    public abstract String getStrategyDescription();

    public Strategy(Table table) {

        this.table = table;
        this.playerHand = table.getHeroHand();
    }

    public Table getTable() {
        return table;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public abstract boolean isActionCorrect(Player.Action action);

    public abstract Player.Action correctAction();

    public abstract String actionExplanation();


}
