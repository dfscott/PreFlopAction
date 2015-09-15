package com.poker.dscott.pfatrainer.service;

import com.poker.dscott.pfatrainer.entity.Blinds;

import java.util.List;

/**
 * Created by dscott on 9/13/2015.
 */
public interface BlindService {

    public List<Blinds> getBlindLevels();
    public Blinds randomizeBlinds();
}
