package blackjack.rules;

import blackjack.cards.Hand;
import blackjack.cards.Rank;

public abstract class BlackjackRuleSet implements RuleSet {
    private final RuleConfig config;

    public BlackjackRuleSet() {
        this(RuleConfig.builder().build());
    }

    public BlackjackRuleSet(RuleConfig config) {
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
}
