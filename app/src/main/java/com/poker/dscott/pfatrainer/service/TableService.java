package com.poker.dscott.pfatrainer.service;

import com.poker.dscott.pfatrainer.entity.Table;

/**
 * Created by dscott on 9/13/2015.
 */
public interface TableService {

    public Table initializeTable();
    public Table initializeTable(int numberOfPlayers);
}
