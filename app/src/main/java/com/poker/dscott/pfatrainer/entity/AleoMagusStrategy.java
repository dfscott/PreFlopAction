package com.poker.dscott.pfatrainer.entity;

/**
 * Created by dscott on 9/15/2015.
 */
public class AleoMagusStrategy extends Strategy {

    boolean isActionDetermined = false;
    Player.Action correctAction = null;

    public AleoMagusStrategy(Table table) {
        super(table);
        isActionDetermined = false;
    }

    @Override
    public String getStrategyName() {
        return "AleoMagus Strategy";
    }

    @Override
    public String getStrategyDescription() {
        return "Strategy based on 2+2 Forum Poster AleoMagus' basic SnG strategy";
    }

    @Override
    public boolean isActionCorrect(Player.Action action) {
        determineAction();
        return false;
    }

    @Override
    public Player.Action correctAction() {
        determineAction();
        return correctAction;
    }

    @Override
    public String actionExplanation() {
        determineAction();
        return null;
    }

    private void determineAction() {

        if (isActionDetermined) {
            return;
        }

        isActionDetermined = true;

        // determine early, late, or bubble strategy
        Table table = getTable();
        if (table.getBubblePlayerCount() == table.getRemainingPlayers()) {
            bubbleStrategy();
        }
        int round = getTable().getBlinds().getLevel();
        if (round < 4) {
            earlyStrategy();
        }
        else {
            lateStrategy();
        }

        // todo - figure out actual strategy (push, call, raise) based on stack-size
    }

    private void bubbleStrategy() {

        correctAction = Player.Action.FOLD;

        Hand hand = getPlayerHand();
        if (hand.isPremiumPair() || hand.isBigSlick()) {
            correctAction = Player.Action.RAISE;
            return;
        }

        Table table = getTable();
        if (table.getNumberOfRaisers() == 0 &&
                hand.isBigSlick()) {
            correctAction = Player.Action.RAISE;
            return;
        }

        int shortStackAmount = table.getShortStackAmount();

        if (table.isOpponentAllIn() &&
            table.getAmountToCall() == table.getShortStackAmount()) {
            // the short stack is all in - we can loosen our call
            if (hand.isBroadway() && isMediumPair(hand)) {
                correctAction = Player.Action.CALL;
            }
        }

        Player hero = table.getHero();
        if (!table.isOpponentAllIn() &&
            (table.getNumberOfRaisers() == 1) &&
                (table.getAmountToCall()*3) < hero.getChipCount()) {
            if (hand.isLittleSlick() ||
                (hand.isPair()
                        && (hand.containsCard(Card.CardRank.JACK))
                        || (hand.containsCard(Card.CardRank.TEN)))) {
                correctAction = Player.Action.CALL;
            }
        }

        if (hero.getChipCount() == table.getShortStackAmount()) {
            // A7+ is required to push -- that's a card value of 21;
            if (hand.isPair() ||
                hand.isSuitedAce() ||
                (hand.containsCard(Card.CardRank.ACE)) && getTotalHandValue(hand) >= 21) {
                correctAction = Player.Action.ALLIN;
            }

        }
    }

    private void earlyStrategy() {

        correctAction = Player.Action.FOLD;

        Hand hand = getPlayerHand();
        if (hand.isPremiumPair()) {
            correctAction = Player.Action.RAISE;
            return;
        }

        Table table = getTable();
        if (table.getNumberOfRaisers() == 2 &&
            !table.isOpponentAllIn() &&
            hand.isBigSlick()) {
            correctAction = Player.Action.RAISE;
            return;
        }
        int position = table.getHeroPosition();
        boolean latePosition =(position > table.getRemainingPlayers() - 4);
        if (!latePosition) {
            return;
        }

        if (table.getNumberOfRaisers() == 0) {
            // only keep going if no one has raised already
            if (isMediumPair(hand)) {
                correctAction = Player.Action.RAISE;
                return;
            }
            if (hand.isLittleSlick() ||
                (isSuitedBroadway(hand))) {
                if (table.getNumberOfLimpers() == 0) {
                    correctAction = Player.Action.RAISE;
                    return;
                }
                else {
                    correctAction = Player.Action.CALL;
                    return;
                }
            }
            if (!latePosition) {
                if (hand.isPair() || hand.isLittleSlick()) {
                    correctAction = Player.Action.CALL;
                }
            }
        }
    }

    private void lateStrategy() {

        correctAction = Player.Action.FOLD;

        Hand hand = getPlayerHand();
        if (hand.isPremiumPair()) {
            correctAction = Player.Action.RAISE;
            return;
        }
        Table table = getTable();
        if (table.getNumberOfRaisers() < 2 &&
                hand.isBigSlick()) {
            correctAction = Player.Action.RAISE;
            return;
        }
        if (table.getNumberOfRaisers() == 0) {
            if (isMediumPair(hand)
                || hand.isLittleSlick()
                || isSuitedBroadway(hand)
                || hand.isRoyalCouple()
                || hand.isAjax()) {
                correctAction = Player.Action.RAISE;
            }
            if (table.getHero().getChipCount() > (table.getTotalChips() / table.getRemainingPlayers())) {
                // good stack and no raisers gives more options
                if (hand.isBroadway() ||
                        (hand.isSuited() && hand.isConnector() && (hand.containsCard(Card.CardRank.NINE) || hand.containsCard(Card.CardRank.EIGHT)))) {
                    if (table.getNumberOfLimpers() > 1) {
                        correctAction = Player.Action.CALL;
                    } else {
                        correctAction = Player.Action.RAISE;
                    }
                }
            }
        }
    }

    private boolean isMediumPair(Hand hand) {
        return (hand.isPair() && hand.getCard1().getCardRank().getValue() > 6);
    }

    private boolean isSuitedBroadway(Hand hand) {
        return hand.isSuited() && hand.isBroadway();
    }

    private int getTotalHandValue(Hand hand) {
        return hand.getCard1().getCardRank().getValue() +
                hand.getCard2().getCardRank().getValue();
    }
}
