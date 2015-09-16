package com.poker.dscott.pfatrainer.service;

import com.poker.dscott.pfatrainer.entity.Card;
import com.poker.dscott.pfatrainer.entity.Hand;

import java.util.Random;

/**
 * Created by dscott on 9/13/2015.
 */
public class HandServiceImpl implements HandService {

    private Random random = new Random(System.currentTimeMillis());

    @Override
    public Hand generateRandomHand() {

        int cardRank = random.nextInt(13) + 2;
        int cardSuit = random.nextInt(4) + 1;
        Card card1 = new Card(cardRank, cardSuit);
        Card card2;
        do {
            cardRank = random.nextInt(13) + 2;
            cardSuit = random.nextInt(4) + 1;
            card2 = new Card(cardRank, cardSuit);
        } while (card1.equals(card2));

        return new Hand(card1, card2);
    }
}
