package blackjack.game;

import java.util.List;

import blackjack.cards.Card;
import blackjack.cards.Hand;

public class GameState {
    private final Hand playerHand;
    private final Hand dealerHand;

    public GameState(Hand playerHand, Hand dealerHand) {
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
    }

    public List<Card> getPlayerCards() {
        return playerHand.getCards();
    }

    public List<Card> getDealerCards() {
        return dealerHand.getCards();
    }

    public Card getDealerFaceUp() {
        return dealerHand.getCards().get(0);
    }
}
