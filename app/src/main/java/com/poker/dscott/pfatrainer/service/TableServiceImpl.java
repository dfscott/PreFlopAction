package com.poker.dscott.pfatrainer.service;

import com.poker.dscott.pfatrainer.entity.Table;

import java.util.Random;

/**
 * Created by dscott on 9/13/2015.
 */
public class TableServiceImpl implements TableService {

    private Table table;
    private BlindService blindService;

    public BlindService getBlindService() {
        if (blindService == null) {
            blindService = new BlindServiceImpl();
        }
        return blindService;
    }

    @Override
    public Table initializeTable() {
        Random random = new Random();
        int numberOfPlayers = random.nextInt(9) + 1;
        return initializeTable(numberOfPlayers);
    }

    @Override
    public Table initializeTable(int numberOfPlayers) {

        Table table = new Table();
        BlindService blindService = getBlindService();
        int bigBlind = blindService.randomizeBigBlind();

        table.setBB(bigBlind);
        table.setSB(bigBlind/2);

        for (int x = 1;x <= numberOfPlayers;x++) {

        }

        return table;
    }

}
