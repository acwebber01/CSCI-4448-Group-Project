package blackjack.rules;

import blackjack.Hand;
import blackjack.cards.Rank;

public class StandardRuleSet implements RuleSet {
    private final RuleConfig config;

    public StandardRuleSet() {
        this(RuleConfig.builder().build());
    }

    public StandardRuleSet(RuleConfig config) {
        this.config = config;
    }

    @Override
    public RuleConfig config() {
        return config;
    }

    @Override
    public HandEvaluation evaluate(Hand hand) {
        int total = 0;
        int softAces = 0;
        int aceReduction = config.cardValue(Rank.ACE) - 1;

        for (var card : hand.getCards()) {
            total += config.cardValue(card.rank());
            if (card.rank() == Rank.ACE) {
                softAces++;
            }
        }

        while (total > config.bustValue() && softAces > 0 && aceReduction > 0) {
            total -= aceReduction;
            softAces--;
        }

        return new HandEvaluation(total, softAces > 0);
    }

    @Override
    public boolean dealerShouldHit(Hand dealerHand) {
        HandEvaluation evaluation = evaluate(dealerHand);
        if (evaluation.bestValue() < config.dealerStandValue()) {
            return true;
        }

        return config.dealerHitsSoft()
                && evaluation.bestValue() == config.dealerStandValue()
                && evaluation.soft();
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
