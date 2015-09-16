package com.poker.dscott.pfatrainer.entity;

import com.poker.dscott.pfatrainer.utils.FormattingUtils;

/**
 * Created by dscott on 9/13/2015.
 */
public class Card implements Comparable {

    public final static String SPADE_SYMBOL_UNI = "\u2660";
    public final static String CLUB_SYMBOL_UNI = "\u2663";
    public final static String HEART_SYMBOL_UNI = FormattingUtils.RED_TEXT + "\u2665" + FormattingUtils.END_COLORED_TEXT;
    public final static String DIAMOND_SYMBOL_UNI = FormattingUtils.RED_TEXT + "\u2666" + FormattingUtils.END_COLORED_TEXT;

    public enum CardRank {
        ACE('A',14),
        KING('K',13),
        QUEEN('Q',12),
        JACK('J',11),
        TEN('T',10),
        NINE('9',9),
        EIGHT('8',8),
        SEVEN('7',7),
        SIX('6',6),
        FIVE('5',5),
        FOUR('4',4),
        THREE('3',3),
        TWO('2',2);


        private char rank;
        private int value;

        CardRank(char rank, int rankValue) {
            this.rank = rank;
            this.value = rankValue;
        }

        public char getRank() {
            return rank;
        }

        public int getValue() {
            return value;
        }
    }

    public enum CardSuit {
        SPADES('S'),
        HEART('H'),
        CLUBS('C'),
        DIAMONDS('D');

        private char suitAbbrev;

        CardSuit(char suitAbbrev) {
            this.suitAbbrev = suitAbbrev;
        }

        public char getSuitAbbrev() {
            return suitAbbrev;
        }
    }

    private CardRank cardRank;
    private CardSuit cardSuit;

    public Card(CardRank cardRank, CardSuit cardSuit) {
        this.cardRank = cardRank;
        this.cardSuit = cardSuit;
    }

    public Card(int rankValue, int suitIndex) {
        this.cardRank = getCardRank(rankValue);
        this.cardSuit = CardSuit.values()[suitIndex - 1];
    }

    public CardRank getCardRank() {
        return cardRank;
    }

    public void setCardRank(CardRank cardRank) {
        this.cardRank = cardRank;
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public void setCardSuit(CardSuit cardSuit) {
        this.cardSuit = cardSuit;
    }

    @Override
    public int compareTo(Object another) {
        Card otherCard = (Card) another;
        return ((Integer) cardRank.getValue()).compareTo(otherCard.getCardRank().getValue());
    }

    public static CardRank getCardRank(char rank) {

        for (CardRank cardRank : CardRank.values()) {
            if (cardRank.getRank() == rank) {
                return cardRank;
            }
        }
        return null;
    }

    public static CardRank getCardRank(int rankValue) {

        for (CardRank cardRank : CardRank.values()) {
            if (cardRank.getValue() == rankValue) {
                return cardRank;
            }
        }
        return null;
    }

    public static CardSuit getCardSuit(char suit) {

        for (CardSuit cardSuit : CardSuit.values()) {
            if (cardSuit.getSuitAbbrev() == suit) {
                return cardSuit;
            }
        }
        return null;
    }


    public String getCardString() {
        return getCardRank().getRank() + String.valueOf(getCardSuit().getSuitAbbrev());
    }

    public String getHTMLUnicodeCardString() {
        String cardString = String.valueOf(getCardRank().getRank());
        switch (getCardSuit()) {
            case SPADES:
                cardString += SPADE_SYMBOL_UNI;
                break;
            case HEART:
                cardString += HEART_SYMBOL_UNI;
                break;
            case CLUBS:
                cardString += CLUB_SYMBOL_UNI;
                break;
            case DIAMONDS:
                cardString += DIAMOND_SYMBOL_UNI;
                break;
            default:
                cardString += "??";
        }
        return cardString;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (cardRank != card.cardRank) return false;
        return cardSuit == card.cardSuit;

    }

    @Override
    public int hashCode() {
        int result = cardRank != null ? cardRank.hashCode() : 0;
        result = 31 * result + (cardSuit != null ? cardSuit.hashCode() : 0);
        return result;
    }
}
