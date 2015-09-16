package com.poker.dscott.pfatrainer.service;

import android.util.Log;

import com.poker.dscott.pfatrainer.entity.Card;
import com.poker.dscott.pfatrainer.entity.Hand;

import junit.framework.TestCase;

/**
 * Created by dscott on 9/15/2015.
 */
public class TestHandService extends TestCase {

    public HandService handService = new HandServiceImpl();

    public void testHandGeneration() {

        int counter = 0;
        Hand hand;
        do {
            hand = handService.generateRandomHand();
            counter++;
            if (hand.getCard1().getCardRank() == Card.CardRank.ACE) {
                break;
            }
            if (hand.getCard2().getCardRank() == Card.CardRank.ACE) {
                break;
            }
        } while (counter < 5000);

        Log.d("TestHandService","\n*********  counter " + counter + " hand : " + hand.getHandDescription() + "\n");
    }

    public void testHandClassification() {

        for (int x=1;x<21;x++) {

            Hand hand = handService.generateRandomHand();
            Log.d("TestHandService","\n*********  hand : " + hand.getHandDescription() + "\n");
            Log.d("TestHandService","\n suited? : " + hand.isSuited() + "\n");
            Log.d("TestHandService","\n pair? : " + hand.isPair() + "\n");
            Log.d("TestHandService","\n premium pair? : " + hand.isPremiumPair() + "\n");
            Log.d("TestHandService","\n connector? : " + hand.isConnector() + "\n");
            Log.d("TestHandService","\n big slick? : " + hand.isBigSlick() + "\n");
            Log.d("TestHandService","\n chick slick? : " + hand.isChickSlick() + "\n");
            Log.d("TestHandService","\n broadway? : " + hand.isBroadway() + "\n");
            Log.d("TestHandService","\n suited ace? : " + hand.isSuitedAce() + "\n\n");


        }
    }
}
