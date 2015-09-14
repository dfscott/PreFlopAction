package com.poker.dscott.pfatrainer.service;

import android.util.Log;

import com.poker.dscott.pfatrainer.entity.FullTable;
import com.poker.dscott.pfatrainer.entity.Table;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by dscott on 9/13/2015.
 */
public class TestTableService extends TestCase {

    public void testInitializeTable() {

        TableService tableService = new TableServiceImpl();
        Table table = new FullTable();
        tableService.initializeTable(table, 7);
        Assert.assertTrue(table.getNumberOfPlayers() == 7);
        tableService.generateAction(table);
        String tableStatus = table.getTableStatus();
        Assert.assertTrue(tableStatus.length() > 0);
        Log.d("MainActivity","table status is : " + tableStatus);
    }


}
