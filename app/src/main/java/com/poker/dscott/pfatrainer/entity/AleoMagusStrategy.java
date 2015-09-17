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

        // todo -dfs- temporarily remove recheck for debugging
/*
        if (isActionDetermined) {
            return;
        }
*/

        isActionDetermined = true;

        // default is fold for all but BB with no raisers, which is check
        Table table = getTable();
        correctAction = Player.Action.FOLD;
        if (table.isHeroBB() && table.getNumberOfRaisers() == 0) {
            correctAction = Player.Action.CHECK;
        }

        // determine early, late, or bubble strategy
        if (table.getBubblePlayerCount() == table.getRemainingPlayers()) {
            bubbleStrategy();
        }
        else {
            int round = table.getBlinds().getLevel();
            if (round < 4) {
                earlyStrategy();
            } else {
                lateStrategy();
            }
        }

        if ((correctAction == Player.Action.RAISE) ||
                (correctAction == Player.Action.CALL)) {
            if (table.getHero().getChipCount() < (7 * table.getBB())) {
                correctAction = Player.Action.ALLIN;
            }
        }

    }

    private void bubbleStrategy() {

        Hand hand = getPlayerHand();

        boolean isBigSlick = hand.isBigSlick();
        if (hand.isPremiumPair() || isBigSlick) {
            correctAction = Player.Action.RAISE;
            return;
        }

        Table table = getTable();
        if (table.getNumberOfRaisers() == 0 &&
                isBigSlick) {
            correctAction = Player.Action.RAISE;
            return;
        }

        int shortStackAmount = table.getShortStackAmount();
        int heroStack = table.getHero().getChipCount();
        int amountToCall = table.getAmountToCall();
        boolean isOpponentAllIn = table.isOpponentAllIn();

        if (isOpponentAllIn &&
            amountToCall == shortStackAmount) {
            // the short stack is all in - we can loosen our call
            if (hand.isBroadway() && isMediumPair(hand)) {
                correctAction = Player.Action.CALL;
                return;
            }
        }

        if (!isOpponentAllIn &&
            (table.getNumberOfRaisers() == 1) &&
                (amountToCall*3) < heroStack) {
            if (hand.isLittleSlick() ||
                (hand.isPair()
                        && (hand.containsCard(Card.CardRank.JACK))
                        || (hand.containsCard(Card.CardRank.TEN)))) {
                correctAction = Player.Action.CALL;
                return;
            }
        }

        if (heroStack == shortStackAmount) {
            // A7+ is required to push -- that's a card value of 21;
            if (hand.isPair() ||
                hand.isSuitedAce() ||
                (hand.containsCard(Card.CardRank.ACE)) && getTotalHandValue(hand) >= 21) {
                correctAction = Player.Action.ALLIN;
            }

        }
    }

    private void earlyStrategy() {

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

        boolean latePosition = table.isHeroLatePosition();
        if (!latePosition) {
            if (hand.isPair() || hand.isLittleSlick()) {
                correctAction = Player.Action.CALL;
            }
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
                if (table.getNumberOfCallers() == 0) {
                    correctAction = Player.Action.RAISE;
                }
                else {
                    correctAction = Player.Action.CALL;
                }
            }
        }
    }

    private void lateStrategy() {

        Hand hand = getPlayerHand();
        if (hand.isPremiumPair()) {
            correctAction = Player.Action.RAISE;
            return;
        }
        Table table = getTable();
        int numberOfRaisers = table.getNumberOfRaisers();

        if (numberOfRaisers < 2 &&
                hand.isBigSlick()) {
            correctAction = Player.Action.RAISE;
            return;
        }
        if ((numberOfRaisers == 0) || (table.getAmountToCall() <= table.getBB())) {
            if (isMediumPair(hand)
                || hand.isLittleSlick()
                || isSuitedBroadway(hand)
                || hand.isRoyalCouple()
                || hand.isAjax()) {
                correctAction = Player.Action.RAISE;
                return;
            }
            if (table.getHero().getChipCount() > (table.getTotalChips() / table.getRemainingPlayers())) {
                // good stack and no raisers gives more options
                if (hand.isBroadway() ||
                        (hand.isSuited() && hand.isConnector() && (hand.containsCard(Card.CardRank.NINE) || hand.containsCard(Card.CardRank.EIGHT)))) {
                    if (table.getNumberOfCallers() > 1) {
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
