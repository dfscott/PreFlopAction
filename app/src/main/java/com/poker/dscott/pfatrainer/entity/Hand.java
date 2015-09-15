package com.poker.dscott.pfatrainer.entity;

/**
 * Created by dscott on 9/13/2015.
 */
public class Hand {

    private Card card1;
    private Card card2;

    public Hand(Card card1, Card card2) {
        this.card1 = card1;
        this.card2 = card2;
    }

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    public String getHandDescription() {
        return card1.getCardString() + ", " + card2.getCardString();
    }

    public String getHTMLUniHandDescription() {
        return card1.getHTMLUnicodeCardString() + card2.getHTMLUnicodeCardString();
    }

    public boolean isPair() {
        return (card1.getCardRank().equals(card2.getCardRank()));
    }

    public boolean isSuited() {
        return (card1.getCardSuit().equals(card2.getCardSuit()));
    }

    public boolean isConnector() {
        int diff = Math.abs(card1.getCardRank().getValue() - card2.getCardRank().getValue());
        if (diff == 1) {
            return true;
        }
        // check for wheel
        if (containsCard(Card.CardRank.ACE) && containsCard(Card.CardRank.TWO)) {
            return true;
        }
        return false;
    }

    public boolean isPremiumPair() {
        return (isPair() && card1.getCardRank().getValue() > Card.CardRank.JACK.getValue());
    }

    public boolean isBigSlick() {

        if (containsCard(Card.CardRank.ACE) && containsCard(Card.CardRank.KING)) {
            return true;
        }
        return false;
    }

    public boolean isSuitedAce() {
        return (isSuited() && containsCard(Card.CardRank.ACE));
    }

    public boolean isBroadway() {
        int minBroadwayValue = Card.CardRank.TEN.getValue();
        return ((card1.getCardRank().getValue() >= minBroadwayValue) &&
                (card2.getCardRank().getValue() >= minBroadwayValue));
    }

    public boolean containsCard(Card.CardRank cardRank) {
        return ((card1.getCardRank() == cardRank) || (card2.getCardRank() == cardRank));
    }
}
