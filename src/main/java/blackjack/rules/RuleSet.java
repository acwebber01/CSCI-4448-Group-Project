package blackjack.rules;

import blackjack.Hand;

public interface RuleSet {
    RuleConfig config();

    HandEvaluation evaluate(Hand hand);

    default int calculateHandValue(Hand hand) {
        return evaluate(hand).bestValue();
    }

    default boolean isBust(Hand hand) {
        return calculateHandValue(hand) > config().bustValue();
    }

    default boolean isBlackjack(Hand hand) {
        return calculateHandValue(hand) == config().blackjackValue() && hand.size() == 2;
    }

    boolean dealerShouldHit(Hand dealerHand);

    RoundOutcome resolveOutcome(Hand playerHand, Hand dealerHand);
}
