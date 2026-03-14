package blackjack.cards;

import java.util.List;

public class DeckFactory {
    private static List<Card> createStandardListOfCards() {
        var cards = new java.util.ArrayList<Card>();
        for (var suit : Suit.values()) {
            for (var rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        return cards;
    }

    public Deck createStandardDeck() {
        return new Deck(createStandardListOfCards());
    }

    public Deck createShoe(int numberOfDecks) {
        if (numberOfDecks <= 0) {
            throw new IllegalArgumentException("Number of decks must be greater than zero");
        }
        var cards = new java.util.ArrayList<Card>();
        for (int i = 0; i < numberOfDecks; i++) {
            cards.addAll(createStandardListOfCards());
        }
        return new Deck(cards);
    }
}
