package blackjack.cards;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
    }

    public Deck(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Cannot draw from an empty deck");
        }
        return cards.remove(cards.size() - 1);
    }

    public Card peek() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Cannot peek at an empty deck");
        }
        return cards.get(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
