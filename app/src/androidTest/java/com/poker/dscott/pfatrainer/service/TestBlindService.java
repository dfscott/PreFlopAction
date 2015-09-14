package com.poker.dscott.pfatrainer.service;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.List;

/**
 * Created by dscott on 9/13/2015.
 */
public class TestBlindService extends TestCase {

    public void testRandomizeBigBlind() throws Exception {

        BlindService blindService = new BlindServiceImpl();
        List<Integer> blindLevels = blindService.getBigBlindLevels();
        Assert.assertTrue(blindLevels.size() > 0);

        int bigBlind = blindService.randomizeBigBlind();
        Assert.assertTrue(bigBlind > 0);
    }
}
