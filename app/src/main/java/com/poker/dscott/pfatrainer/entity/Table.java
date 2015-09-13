package com.poker.dscott.pfatrainer.entity;

import com.poker.dscott.pfatrainer.App;
import com.poker.dscott.pfatrainer.R;

import java.util.List;

/**
 * Created by dscott on 9/13/2015.
 */
public class Table {

    private int numberOfPlayers;
    private int BB;
    private int SB;
    private List<Player> players;

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

    public String getTableStatus() {

        String tableStatusString = App.getContext().getString(R.string.num_players_display,
                getBB(), getSB(), getNumberOfPlayers());
//        String tableStatusString = "players = " + getNumberOfPlayers();
        return tableStatusString;
    }
}
