package com.poker.dscott.pfatrainer.service;

import com.poker.dscott.pfatrainer.entity.Hand;
import com.poker.dscott.pfatrainer.entity.Player;
import com.poker.dscott.pfatrainer.entity.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dscott on 9/13/2015.
 */
public class TableServiceImpl implements TableService {

    Random random = new Random(System.currentTimeMillis());
    private Table table;
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
    public Table initializeTable() {
        int numberOfPlayers = random.nextInt(8) + 2;
        return initializeTable(numberOfPlayers);
    }

    @Override
    public Table initializeTable(int numberOfPlayers) {

        Table table = new Table();

        table.setNumberOfPlayers(numberOfPlayers);
        BlindService blindService = getBlindService();
        int bigBlind = blindService.randomizeBigBlind();

        table.setBB(bigBlind);
        table.setSB(bigBlind / 2);

        Hand hand = getHandService().generateRandomHand();
        table.setHeroHand(hand);
        
        table.setHeroPosition(random.nextInt(numberOfPlayers - 1));

        randomizeChipStacks(table);

        return table;
    }

    private void randomizeChipStacks(Table table) {

        int numberOfPlayers = table.getNumberOfPlayers();
        int totalChips = numberOfPlayers * Table.startingChips;

        List<Player> playerList = new ArrayList<>();
        for (int x = 1;x <= numberOfPlayers;x++) {
            Player player = new Player();
            player.setChipCount(randomizeChipStack(totalChips, numberOfPlayers - x));
            player.setPosition(x);
            playerList.add(player);
        }
        table.setPlayers(playerList);

    }

    private int randomizeChipStack(int chipsRemaining, int playersRemaining) {
        int upperLimit = chipsRemaining - (playersRemaining * Table.minimumPlayerChips);
        return random.nextInt(upperLimit - 100) + 100;
    }
}
