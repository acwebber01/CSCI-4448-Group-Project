package blackjack.participants;

import blackjack.cards.Card;
import blackjack.cards.Hand;

public abstract class Participant {
    private final String name;
    private final Hand hand;

    public Participant(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    public Participant(String name, Hand hand) {
        this.name = name;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void receiveCard(Card card) {
        hand.addCard(card);
    }

    public void clearHand() {
        hand.clear();
    }
}
