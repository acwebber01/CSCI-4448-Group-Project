package blackjack.rules;

import blackjack.cards.Hand;

public class Spanish21RuleSet extends BlackjackRuleSet {
    public Spanish21RuleSet() {
        super(RuleConfig.builder()
                .dealerHitsSoft(true)
                .build());
    }

    public Spanish21RuleSet(RuleConfig config) {
        super(config);
    }

    @Override
    public RoundOutcome resolveOutcome(Hand playerHand, Hand dealerHand) {
        HandEvaluation playerEvaluation = evaluate(playerHand);
        HandEvaluation dealerEvaluation = evaluate(dealerHand);

        if (playerEvaluation.bestValue() <= config().bustValue()
                && dealerEvaluation.bestValue() <= config().bustValue()
                && playerEvaluation.bestValue() == config().blackjackValue()
                && dealerEvaluation.bestValue() == config().blackjackValue()) {
            return RoundOutcome.PLAYER_WIN;
        }

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

        if (playerEvaluation.bestValue() > dealerEvaluation.bestValue()) {
            return RoundOutcome.PLAYER_WIN;
        }

        if (dealerEvaluation.bestValue() > playerEvaluation.bestValue()) {
            return RoundOutcome.DEALER_WIN;
        }

        return RoundOutcome.PUSH;
    }
}
