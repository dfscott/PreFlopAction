package com.poker.dscott.pfatrainer.service;

import com.poker.dscott.pfatrainer.entity.Hand;
import com.poker.dscott.pfatrainer.entity.Player;
import com.poker.dscott.pfatrainer.entity.Table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.poker.dscott.pfatrainer.entity.Player.Action.CALL;
import static com.poker.dscott.pfatrainer.entity.Player.Action.FOLD;
import static com.poker.dscott.pfatrainer.entity.Player.Action.RAISE;

/**
 * Created by dscott on 9/13/2015.
 */
public class TableServiceImpl implements TableService {

    Random random = new Random(System.currentTimeMillis());
    private BlindService blindService;
    private HandService handService;

    public BlindService getBlindService() {
        if (blindService == null) {
            blindService = new BlindServiceImpl();
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
        int numberOfPlayers = random.nextInt(8) + 2;
        initializeTable(table, numberOfPlayers);
    }

    @Override
    public void initializeTable(Table table, int numberOfPlayers) {

        table.setNumberOfPlayers(numberOfPlayers);
        BlindService blindService = getBlindService();
        int bigBlind = blindService.randomizeBigBlind();

        table.setBB(bigBlind);
        table.setSB(bigBlind / 2);

        Hand hand = getHandService().generateRandomHand();
        table.setHeroHand(hand);
        
        table.setHeroPosition(random.nextInt(numberOfPlayers - 1) + 1);

        loadPlayerStacks(table);

    }

    private void loadPlayerStacks(Table table) {

        int numberOfPlayers = table.getNumberOfPlayers();
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
        for (Player player : table.getPlayers()) {
            if (player.getPosition() == table.getHeroPosition()) {
                break;
            }
            float betChance = 0.2f;
            if (currentBet == 0) {
                betChance += 0.10;
            }
            if (Math.random() > betChance) {
                player.setAction(FOLD);
                player.setBet(0);
            }
            else {
                if (Math.random() > .7) {
                    player.setAction(CALL);
                    playerBet = currentBet;
                    isTableOpened = true;
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
}
