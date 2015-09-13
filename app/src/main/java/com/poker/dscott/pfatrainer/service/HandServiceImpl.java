package com.poker.dscott.pfatrainer.service;

import com.poker.dscott.pfatrainer.entity.Card;
import com.poker.dscott.pfatrainer.entity.Hand;

import java.util.Random;

/**
 * Created by dscott on 9/13/2015.
 */
public class HandServiceImpl implements HandService {

    @Override
    public Hand generateRandomHand() {

        Random random = new Random();
        int cardRank = random.nextInt(12) + 2;
        int cardSuit = random.nextInt(3) + 1;
        Card card1 = new Card(cardRank, cardSuit);

        cardRank = random.nextInt(12) + 2;
        cardSuit = random.nextInt(3) + 1;
        Card card2 = new Card(cardRank, cardSuit);

        return new Hand(card1, card2);
    }
}
