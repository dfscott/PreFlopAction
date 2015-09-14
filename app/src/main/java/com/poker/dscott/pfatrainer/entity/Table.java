package com.poker.dscott.pfatrainer.entity;

import com.poker.dscott.pfatrainer.App;
import com.poker.dscott.pfatrainer.R;

import java.text.DecimalFormat;
import java.util.List;

import static com.poker.dscott.pfatrainer.entity.Player.Action.CALL;
import static com.poker.dscott.pfatrainer.entity.Player.Action.FOLD;
import static com.poker.dscott.pfatrainer.entity.Player.Action.RAISE;

/**
 * Created by dscott on 9/13/2015.
 */
public abstract class Table {

    public static String newLine = System.getProperty("line.separator");

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

    public String getTextPosition(int tablePosition) {

        String position;
        if (tablePosition == numberOfPlayers) {
            return App.getContext().getString(R.string.BB);
        }
        if (tablePosition == numberOfPlayers - 1) {
            return App.getContext().getString(R.string.SB);
        }
        if (tablePosition == numberOfPlayers - 2) {
            return App.getContext().getString(R.string.BTN);
        }
        if (tablePosition == numberOfPlayers - 3) {
            return App.getContext().getString(R.string.CO);
        }
        if (tablePosition == 1) {
            return App.getContext().getString(R.string.UTG);
        }
        if (tablePosition == 2) {
            if (numberOfPlayers == 6) {
                return App.getContext().getString(R.string.MP);
            }
            else {
                return App.getContext().getString(R.string.UTG1);
            }
        }
        if (tablePosition == 3) {
            if (numberOfPlayers == 7) {
                return App.getContext().getString(R.string.MP);
            }
            else {
                if (numberOfPlayers == 8) {
                    return App.getContext().getString(R.string.MP1);
                }
                else {
                    return App.getContext().getString(R.string.UTG2);
                }
            }
        }
        if (tablePosition == 4) {
            if (numberOfPlayers == 8) {
                return App.getContext().getString(R.string.MP2);
            }
            else {
                return App.getContext().getString(R.string.MP1);
            }
        }
        if (tablePosition == 5) {
            return App.getContext().getString(R.string.MP2);
        }
        else {
            return App.getContext().getString(R.string.MP3);
        }
    }

    public String getTableStatus() {

        DecimalFormat stackFormat = new DecimalFormat("#,###,###");
        DecimalFormat bbFormat = new DecimalFormat("#,###,###.#");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(App.getContext().getString(R.string.num_players_display, getSB(), getBB(), getNumberOfPlayers()))
                    .append(newLine)
                    .append(newLine);
        for (Player player : getPlayers()) {
            int playerPosition = player.getPosition();
            if (playerPosition  == heroPosition) {
                stringBuilder.append(App.getContext().getString(R.string.HERO))
                                .append(" (");
            }
            stringBuilder.append(getTextPosition(playerPosition));
            if (playerPosition == heroPosition) {
                stringBuilder.append(")");
            }
            stringBuilder.append(": ");
            int chipCount = player.getChipCount();
            stringBuilder.append(stackFormat.format(chipCount));
            float numOfBBs = (float) chipCount / getBB();
            stringBuilder.append(" (")
                        .append(bbFormat.format(numOfBBs))
                        .append(" ")
                        .append(App.getContext().getString(R.string.bb))
                        .append(")")
                        .append(newLine);
        }
        stringBuilder.append(newLine)
                .append(App.getContext().getString(R.string.hero_display,
                        getTextPosition(heroPosition),
                        heroHand.getHandDescription()))
                .append(newLine);
        stringBuilder.append(getActionString());
        return stringBuilder.toString();
    }

    private String getActionString() {

        StringBuilder stringBuilder = new StringBuilder("Preflop: ");
        String foldString = "";
        int numberOfFolds = 0;
        for (Player player : getPlayers()) {
            if (player.getPosition() == heroPosition) {
                break;
            }
            if (player.getAction() == FOLD) {
                if (numberOfFolds == 0) {
                    numberOfFolds = 1;
                    foldString = App.getContext().getString(R.string.folds, getTextPosition(player.getPosition()));
                }
                else {
                    numberOfFolds += 1;
                    foldString = App.getContext().getString(R.string.multi_fold, numberOfFolds);
                }
                continue;
            }
            else {
                if (foldString.length() > 0) {
                    stringBuilder.append(foldString)
                                .append(", ");
                    foldString = "";
                    numberOfFolds = 0;
                }
            }
            if (player.getAction() == CALL) {
                stringBuilder.append(App.getContext().getString(R.string.calls, getTextPosition(player.getPosition()), player.getBet()));
            }
            if (player.getAction() == RAISE) {
                stringBuilder.append(App.getContext().getString(R.string.raises, getTextPosition(player.getPosition()), player.getBet()));
            }
            if (player.isAllIn()) {
                stringBuilder.append(" ")
                            .append(App.getContext().getString(R.string.allin));
            }
            stringBuilder.append(", ");
        }
        if (foldString.length() > 0) {
            stringBuilder.append(foldString);
        }
        String bettingSequence = stringBuilder.toString();
        if ((heroPosition > 1) && (foldString.length() == 0)) {
            bettingSequence = bettingSequence.trim().substring(0, bettingSequence.length() - 2);
        }
        return bettingSequence;
    }
}
