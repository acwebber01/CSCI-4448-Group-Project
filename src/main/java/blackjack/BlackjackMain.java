package blackjack;

import blackjack.cards.Deck;
import blackjack.cards.DeckFactory;
import blackjack.game.GameController;
import blackjack.rules.StandardRuleSet;
import blackjack.ui.ConsoleView;
import blackjack.participants.*;

public class BlackjackMain {
    private GameController gameController;
    private ConsoleView consoleView = new ConsoleView();
    private DeckFactory deckFactory = new DeckFactory();
    private Player player = new Player();
    private Dealer dealer = new Dealer();
    private StandardRuleSet ruleSet = new StandardRuleSet();

    public void run() {
        do {
            Deck deck = deckFactory.createStandardDeck();
            deck.shuffle();
            gameController = new GameController(
                    deck,
                    ruleSet,
                    player,
                    dealer,
                    consoleView);
            gameController.registerObserver(consoleView);
            gameController.playRound();
            player.clearHand();
            dealer.clearHand();
        } while (consoleView.getPlayAgainChoice());
    }

    public static void main(String[] args) {
        new BlackjackMain().run();
    }
}
