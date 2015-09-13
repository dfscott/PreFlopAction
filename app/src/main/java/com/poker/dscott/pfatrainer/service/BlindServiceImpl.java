package com.poker.dscott.pfatrainer.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by dscott on 9/13/2015.
 */
public class BlindServiceImpl implements BlindService {

    private Integer[] blindLevels = {20,30,50,100,150,200,300};

    @Override
    public List<Integer> getBigBlindLevels() {
        return Arrays.asList(blindLevels);
    }

    @Override
    public int randomizeBigBlind() {
        int range = blindLevels.length;
        Random random = new Random();
        int index = random.nextInt(range-1);
        return blindLevels[index];
    }
}
