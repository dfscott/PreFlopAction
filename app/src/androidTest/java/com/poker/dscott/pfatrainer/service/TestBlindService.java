package com.poker.dscott.pfatrainer.service;

import com.poker.dscott.pfatrainer.entity.Blinds;

import junit.framework.TestCase;

import java.util.List;

/**
 * Created by dscott on 9/13/2015.
 */
public class TestBlindService extends TestCase {

    public void testRandomizeBigBlind() throws Exception {

        BlindService blindService = new WPNBlindServiceImpl();
        List<Blinds> blindLevels = blindService.getBlindLevels();
        assertTrue(blindLevels.size() > 0);

        Blinds blinds = blindService.randomizeBlinds();
        assertNotNull(blinds);
        assertTrue(blinds.getBigBlindAmount() > 0);
        assertTrue(blinds.getSmallBlindAmount() > 0);
        assertTrue(blinds.getLevel() > 0);
        assertTrue(blinds.getLevel() <= blindLevels.size());

    }
}
