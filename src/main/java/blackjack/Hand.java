package blackjack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import blackjack.cards.*;

public class Hand {
    private List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public Hand(Collection<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public void clear() {
        cards.clear();
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}
