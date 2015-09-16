package com.poker.dscott.pfatrainer.service;

import com.poker.dscott.pfatrainer.entity.Blinds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dscott on 9/13/2015.
 */
public class WPNBlindServiceImpl implements BlindService {

    private Integer[] blindLevels = {20,30,50,100,150,200,200,300,400};
    private Integer[] anteLevels = {0,0,0,0,0,0,25,25,50};

    private List<Blinds> blindList = new ArrayList<>();

    public WPNBlindServiceImpl() {

        for (int x=0;x<blindLevels.length;x++) {
            Blinds blinds = new Blinds();
            blinds.setLevel(x+1);
            blinds.setBigBlindAmount(blindLevels[x]);
            blinds.setSmallBlindAmount(blindLevels[x]/2);
            blinds.setAnte(anteLevels[x]);
            blindList.add(blinds);
        }
    }

    @Override
    public Blinds randomizeBlinds() {
        int range = blindList.size();
        Random random = new Random();
        int index = random.nextInt(range);
        return blindList.get(index);
    }

    @Override
    public List<Blinds> getBlindLevels() {
        return blindList;
    }

}
