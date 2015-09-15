package com.poker.dscott.pfatrainer.service;

import com.poker.dscott.pfatrainer.entity.Table;

/**
 * Created by dscott on 9/13/2015.
 */
public interface TableService {

    public void initializeTable(Table table);
    public void initializeTable(Table table, int numberOfPlayers);
    public void generateAction(Table table);
    public String generateTableStatusMessage(Table table);
    public String generateActionMessage(Table table);
    public String getTextPosition(int tablePosition, int numberOfPlayers);
}
