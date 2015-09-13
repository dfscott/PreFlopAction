package com.poker.dscott.pfatrainer.service;

import java.util.List;

/**
 * Created by dscott on 9/13/2015.
 */
public interface BlindService {

    public List<Integer> getBigBlindLevels();
    public int randomizeBigBlind();
}
