package blackjack.rules;

import blackjack.cards.Hand;

public class StandardRuleSet extends BlackjackRuleSet {
    public StandardRuleSet() {
        super(RuleConfig.builder().build());
    }

    public StandardRuleSet(RuleConfig config) {
        super(config);
    }

    @Override
    public RoundOutcome resolveOutcome(Hand playerHand, Hand dealerHand) {
        if (isBust(playerHand)) {
            return RoundOutcome.DEALER_WIN;
        }

        if (isBust(dealerHand)) {
            return RoundOutcome.PLAYER_WIN;
        }

        boolean playerBlackjack = isBlackjack(playerHand);
        boolean dealerBlackjack = isBlackjack(dealerHand);

        if (playerBlackjack && !dealerBlackjack) {
            return RoundOutcome.PLAYER_WIN;
        }

        if (dealerBlackjack && !playerBlackjack) {
            return RoundOutcome.DEALER_WIN;
        }

        int playerValue = calculateHandValue(playerHand);
        int dealerValue = calculateHandValue(dealerHand);

        if (playerValue > dealerValue) {
            return RoundOutcome.PLAYER_WIN;
        }

        if (dealerValue > playerValue) {
            return RoundOutcome.DEALER_WIN;
        }

        return RoundOutcome.PUSH;
    }
}
