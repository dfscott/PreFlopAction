package com.poker.dscott.pfatrainer.service;

import com.poker.dscott.pfatrainer.App;
import com.poker.dscott.pfatrainer.R;
import com.poker.dscott.pfatrainer.entity.Blinds;
import com.poker.dscott.pfatrainer.entity.Hand;
import com.poker.dscott.pfatrainer.entity.Player;
import com.poker.dscott.pfatrainer.entity.Table;
import com.poker.dscott.pfatrainer.utils.FormattingUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.poker.dscott.pfatrainer.entity.Player.Action.CALL;
import static com.poker.dscott.pfatrainer.entity.Player.Action.FOLD;
import static com.poker.dscott.pfatrainer.entity.Player.Action.RAISE;
import static com.poker.dscott.pfatrainer.utils.FormattingUtils.STRONG_BEGIN;
import static com.poker.dscott.pfatrainer.utils.FormattingUtils.STRONG_END;

/**
 * Created by dscott on 9/13/2015.
 */
public class TableServiceImpl implements TableService {

    private static final String LINE_BREAK = "<BR>";
    
    Random random = new Random(System.currentTimeMillis());
    private BlindService blindService;
    private HandService handService;

    public BlindService getBlindService() {
        if (blindService == null) {
            blindService = new WPNBlindServiceImpl();
        }
        return blindService;
    }

    public HandService getHandService() {
        if (handService == null) {
            handService = new HandServiceImpl();
        }
        return handService;
    }

    @Override
    public void initializeTable(Table table) {
        int remainingPlayers = random.nextInt(table.getMaxPlayers() + 1 - table.getMinPlayers())
                                            + table.getMinPlayers();
        initializeTable(table, remainingPlayers);
    }

    @Override
    public void initializeTable(Table table, int remainingPlayers) {

        table.setRemainingPlayers(remainingPlayers);
        BlindService blindService = getBlindService();

        table.setBlinds(blindService.randomizeBlinds());

        Hand hand = getHandService().generateRandomHand();
        table.setHeroHand(hand);
        
        table.setHeroPosition(random.nextInt(remainingPlayers) + 1);

        loadPlayerStacks(table);

    }

    private void loadPlayerStacks(Table table) {

        int numberOfPlayers = table.getRemainingPlayers();
        int remainingChips = table.getTotalChips();
        List<Integer> chipList = new ArrayList<>();

        for (int x = 1;x <= numberOfPlayers;x++) {
            int playerChips = remainingChips;
            if (x < numberOfPlayers) {
                playerChips = randomizeChipStack(remainingChips, numberOfPlayers - x);
            }
            chipList.add(new Integer(playerChips));
            remainingChips -= playerChips;
        }

        Collections.shuffle(chipList, new Random(System.currentTimeMillis()));

        List<Player> playerList = new ArrayList<>();
        for (int x = 1;x <= numberOfPlayers;x++) {
            Player player = new Player();
            player.setChipCount(chipList.get(x - 1));
            player.setPosition(x);
            if (player.getPosition() == numberOfPlayers) {
                player.setBet(table.getBB());
            }
            else {
                if (player.getPosition() == (numberOfPlayers - 1)) {
                    player.setBet(table.getSB());
                }
            }
            playerList.add(player);
        }
        table.setPlayers(playerList);
    }

    private int randomizeChipStack(int chipsRemaining, int playersRemaining) {
        int upperLimit = chipsRemaining - (playersRemaining * Table.minimumPlayerChips);
        return random.nextInt(upperLimit - 100) + 100;
    }

    @Override
    public void generateAction(Table table) {

        int currentBet = table.getBB();
        int playerBet = 0;
        int pot = currentBet + table.getSB();
        boolean isTableOpened = false;
        table.setOpponentAllIn(false);

        for (Player player : table.getPlayers()) {
            if (player.getPosition() == table.getHeroPosition()) {
                table.setAmountToCall(currentBet);
                break;
            }
            // default bet chance for anyone is 20%
            float betChance = 0.2f;
            if (!isTableOpened) {
                // first in and less than 5 BB, is 90% to bet
                if (player.getChipCount() < (5 * table.getBB())) {
                    betChance = 0.9f;
                }
                else {
                    // first in vig adds 10% chance to bet
                    betChance += 0.10f;
                }
            }
            if (Math.random() > betChance) {
                player.setAction(FOLD);
                player.setBet(0);
            }
            else {
                if (Math.random() > .7) {
                    player.setAction(CALL);
                    playerBet = currentBet;
                }
                else {
                    player.setAction(RAISE);
                    if (!isTableOpened) {
                        if (Math.random() > .2) {
                            playerBet = table.getBB() * 3;
                        }
                        else {
                            playerBet = table.getBB() * 2;
                        }
                    }
                    else {
                        if (Math.random() > 0.5) {
                            playerBet = currentBet * 2;
                        }
                        else {
                            playerBet = pot;
                        }
                    }
                }
                if (playerBet > player.getChipCount()) {
                    playerBet = player.getChipCount();
                    table.setOpponentAllIn(true);
                }
                player.setBet(playerBet);
                if (playerBet > currentBet) {
                    currentBet = playerBet;
                }
                isTableOpened = true;
                pot += currentBet;
            }
        }
    }

    @Override
    public String generateTableStatusMessage(Table table) {

        DecimalFormat stackFormat = new DecimalFormat("#,###,###");
        DecimalFormat bbFormat = new DecimalFormat("#,###,###.#");

        StringBuilder stringBuilder = new StringBuilder();

        Blinds blinds = table.getBlinds();
        stringBuilder.append(App.getContext().getString(R.string.blinds_display,
                                                        blinds.getLevel(),
                                                        blinds.getSmallBlindAmount(),
                                                        blinds.getBigBlindAmount()));
        if (blinds.getAnte() > 0) {
            stringBuilder.append(App.getContext().getString(R.string.ante_display, blinds.getAnte()));
        }
        stringBuilder.append(App.getContext().getString(R.string.num_players_display, table.getRemainingPlayers()))
                .append(LINE_BREAK)
                .append(LINE_BREAK);
        int heroPosition = table.getHeroPosition();
        boolean isLineStrong = false;
        for (Player player : table.getPlayers()) {
            if ((player.getAction() != Player.Action.FOLD) &&
                (player.getAction() != Player.Action.NO_ACTION)) {
                stringBuilder.append(STRONG_BEGIN);
                isLineStrong = true;
            }
            int playerPosition = player.getPosition();
            if (playerPosition  == heroPosition) {
                stringBuilder.append(STRONG_BEGIN)
                        .append(App.getContext().getString(R.string.HERO))
                        .append(" (");
                isLineStrong = true;
            }
            stringBuilder.append(getTextPosition(playerPosition, table.getRemainingPlayers()));
            if (playerPosition == heroPosition) {
                stringBuilder.append(")");
            }
            stringBuilder.append(": ");
            int chipCount = player.getChipCount();
            stringBuilder.append(stackFormat.format(chipCount));
            float numOfBBs = (float) chipCount / table.getBB();
            stringBuilder.append(" (")
                    .append(bbFormat.format(numOfBBs))
                    .append(" ")
                    .append(App.getContext().getString(R.string.bb))
                    .append(")");
            if (isLineStrong)  {
                stringBuilder.append(STRONG_END);
                isLineStrong = false;
            }
            stringBuilder.append(LINE_BREAK);
        }
        stringBuilder.append(LINE_BREAK)
                .append(App.getContext().getString(R.string.hero_display,
                        getTextPosition(heroPosition, table.getRemainingPlayers()),
                        table.getHeroHand().getHTMLUniHandDescription()))
                .append(LINE_BREAK);
        return stringBuilder.toString();
    }

    @Override
    public String generateActionMessage(Table table) {

        int numberOfPlayers = table.getRemainingPlayers();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FormattingUtils.strongText(App.getContext().getString(R.string.Preflop)));
        String foldString = "";
        int numberOfFolds = 0;
        for (Player player : table.getPlayers()) {
            if (player.getPosition() == table.getHeroPosition()) {
                break;
            }
            if (player.getAction() == FOLD) {
                if (numberOfFolds == 0) {
                    numberOfFolds = 1;
                    foldString = FormattingUtils.foldText(App.getContext().getString(
                            R.string.folds, getTextPosition(player.getPosition(), numberOfPlayers)));
                }
                else {
                    numberOfFolds += 1;
                    foldString = FormattingUtils.foldText(App.getContext().getString(R.string.multi_fold, numberOfFolds));
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
                stringBuilder.append(App.getContext().getString(R.string.calls,
                        getTextPosition(player.getPosition(), numberOfPlayers), player.getBet()));
            }
            if (player.getAction() == RAISE) {
                stringBuilder.append(FormattingUtils.raiseText(App.getContext().getString(R.string.raises,
                        getTextPosition(player.getPosition(), numberOfPlayers), player.getBet())));
            }
            if (player.isAllIn()) {
                String allinText = " " + App.getContext().getString(R.string.allin);
                if (player.getAction() == RAISE) {
                    stringBuilder.append(FormattingUtils.raiseText(allinText));
                }
                else {
                    stringBuilder.append(allinText);
                }
            }
            stringBuilder.append(", ");
        }
        if (foldString.length() > 0) {
            stringBuilder.append(foldString);
        }
        String bettingSequence = stringBuilder.toString();
        if ((table.getHeroPosition() > 1) && (foldString.length() == 0)) {
            bettingSequence = bettingSequence.trim().substring(0, bettingSequence.length() - 2);
        }
        return bettingSequence;
    }

    @Override
    public String getTextPosition(int tablePosition, int numberOfPlayers) {

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



}
