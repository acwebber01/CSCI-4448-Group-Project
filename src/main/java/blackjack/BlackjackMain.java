package blackjack;

import blackjack.cards.Deck;
import blackjack.cards.DeckFactory;
import blackjack.game.GameController;
import blackjack.rules.StandardRuleSet;
import blackjack.ui.ConsoleView;
import blackjack.participants.*;

public class BlackjackMain {
    private GameController gameController;
    private ConsoleView consoleView;
    private DeckFactory deckFactory = new DeckFactory();

    public void run() {
        consoleView = new ConsoleView();
        Deck deck = deckFactory.createStandardDeck();
        deck.shuffle();
        gameController = new GameController(
            deck,
            new StandardRuleSet(),
            new Player(),
            new Dealer(),
            consoleView
        );
        gameController.registerObserver(consoleView);
        gameController.playRound();
    }

    public static void main(String[] args) {
        new BlackjackMain().run();
    }
}
