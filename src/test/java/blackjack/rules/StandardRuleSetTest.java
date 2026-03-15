package blackjack.rules;

import blackjack.cards.*;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StandardRuleSetTest {
    private final StandardRuleSet standardRuleSet = new StandardRuleSet();

    @Test
    void evaluatesMultipleAcesWithoutBusting() {
        Hand hand = hand(
                card(Rank.ACE, Suit.SPADES),
                card(Rank.ACE, Suit.HEARTS),
                card(Rank.NINE, Suit.CLUBS));

        HandEvaluation evaluation = standardRuleSet.evaluate(hand);

        assertEquals(21, evaluation.bestValue());
        assertTrue(evaluation.soft());
    }

    @Test
    void evaluatesAdjustedAceHandAsHardValue() {
        Hand hand = hand(
                card(Rank.ACE, Suit.SPADES),
                card(Rank.SEVEN, Suit.HEARTS),
                card(Rank.NINE, Suit.CLUBS));

        HandEvaluation evaluation = standardRuleSet.evaluate(hand);

        assertEquals(17, evaluation.bestValue());
        assertFalse(evaluation.soft());
    }

    @Test
    void detectsBlackjackWithTwoCards() {
        Hand hand = hand(
                card(Rank.ACE, Suit.SPADES),
                card(Rank.KING, Suit.HEARTS));

        assertTrue(standardRuleSet.isBlackjack(hand));
        assertFalse(standardRuleSet.isBust(hand));
    }

    @Test
    void dealerStandsOnSoft17ByDefault() {
        Hand dealerHand = hand(
                card(Rank.ACE, Suit.DIAMONDS),
                card(Rank.SIX, Suit.CLUBS));

        assertFalse(standardRuleSet.dealerShouldHit(dealerHand));
    }

    @Test
    void dealerCanBeConfiguredToHitSoft17() {
        RuleSet hitSoft17Rules = new StandardRuleSet(
                RuleConfig.builder()
                        .dealerHitsSoft(true)
                        .build());
        Hand dealerHand = hand(
                card(Rank.ACE, Suit.DIAMONDS),
                card(Rank.SIX, Suit.CLUBS));

        assertTrue(hitSoft17Rules.dealerShouldHit(dealerHand));
    }

    @Test
    void customCardValuesComeFromRuleConfig() {
        RuleSet boostedFiveRules = new StandardRuleSet(
                RuleConfig.builder()
                        .cardValue(Rank.FIVE, 6)
                        .build());
        Hand hand = hand(
                card(Rank.FIVE, Suit.SPADES),
                card(Rank.TEN, Suit.HEARTS));

        assertEquals(16, boostedFiveRules.calculateHandValue(hand));
    }

    @Test
    void naturalBlackjackBeatsDealerTwentyOneMadeInThreeCards() {
        Hand playerHand = hand(
                card(Rank.ACE, Suit.SPADES),
                card(Rank.KING, Suit.HEARTS));
        Hand dealerHand = hand(
                card(Rank.SEVEN, Suit.CLUBS),
                card(Rank.SEVEN, Suit.DIAMONDS),
                card(Rank.SEVEN, Suit.HEARTS));

        assertEquals(RoundOutcome.PLAYER_WIN, standardRuleSet.resolveOutcome(playerHand, dealerHand));
    }

    @Test
    void equalNonBlackjackHandsPush() {
        Hand playerHand = hand(
                card(Rank.TEN, Suit.SPADES),
                card(Rank.SEVEN, Suit.HEARTS));
        Hand dealerHand = hand(
                card(Rank.NINE, Suit.CLUBS),
                card(Rank.EIGHT, Suit.DIAMONDS));

        assertEquals(RoundOutcome.PUSH, standardRuleSet.resolveOutcome(playerHand, dealerHand));
    }

    @Test
    void invalidRuleConfigFailsFast() {
        assertThrows(IllegalArgumentException.class, () -> RuleConfig.builder()
                .blackjackValue(22)
                .bustValue(21)
                .build());
    }

    private static Hand hand(Card... cards) {
        return new Hand(List.of(cards));
    }

    private static Card card(Rank rank, Suit suit) {
        return new Card(rank, suit);
    }
}
