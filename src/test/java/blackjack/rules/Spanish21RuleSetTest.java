package blackjack.rules;

import blackjack.Hand;
import blackjack.cards.Card;
import blackjack.cards.Rank;
import blackjack.cards.Suit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Spanish21RuleSetTest {
    private final Spanish21RuleSet spanish21RuleSet = new Spanish21RuleSet();

    @Test
    void defaultsDealerToHitSoft17() {
        Hand dealerHand = hand(
                card(Rank.ACE, Suit.SPADES),
                card(Rank.SIX, Suit.HEARTS));

        assertTrue(spanish21RuleSet.dealerShouldHit(dealerHand));
    }

    @Test
    void playerTwentyOneBeatsDealerTwentyOne() {
        Hand playerHand = hand(
                card(Rank.ACE, Suit.SPADES),
                card(Rank.KING, Suit.HEARTS));
        Hand dealerHand = hand(
                card(Rank.SEVEN, Suit.CLUBS),
                card(Rank.SEVEN, Suit.DIAMONDS),
                card(Rank.SEVEN, Suit.HEARTS));

        assertEquals(RoundOutcome.PLAYER_WIN, spanish21RuleSet.resolveOutcome(playerHand, dealerHand));
    }

    private static Hand hand(Card... cards) {
        return new Hand(List.of(cards));
    }

    private static Card card(Rank rank, Suit suit) {
        return new Card(rank, suit);
    }
}
