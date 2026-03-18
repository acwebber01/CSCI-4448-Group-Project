package blackjack.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import blackjack.cards.Card;
import blackjack.cards.Deck;
import blackjack.participants.Dealer;
import blackjack.participants.Player;
import blackjack.rules.RoundOutcome;
import blackjack.rules.RuleSet;

public class GameController implements GameEventSubject {
    private final Deck deck;
    private final RuleSet ruleSet;
    private final Player player;
    private final Dealer dealer;
    private final InputSource inputSource;
    private final List<GameEventObserver> observers = new ArrayList<>();

    public GameController(Deck deck, RuleSet ruleSet, Player player, Dealer dealer, InputSource inputSource) {
        this.deck = deck;
        this.ruleSet = ruleSet;
        this.player = player;
        this.dealer = dealer;
        this.inputSource = inputSource;
    }

    public void playRound() {
        dealInitialCards();

        if (ruleSet.isBlackjack(player.getHand()) || ruleSet.isBlackjack(dealer.getHand())) {
            endRound();
            return;
        }

        playerTurn();

        if (!ruleSet.isBust(player.getHand())) {
            dealerTurn();
        }

        endRound();
    }

    private void dealInitialCards() {
        player.receiveCard(deck.draw());
        dealer.receiveCard(deck.draw());
        player.receiveCard(deck.draw());
        dealer.receiveCard(deck.draw());
        Map<String, Object> initialData = Map.of(
                "playerCards", player.getHand().getCards(),
                "dealerFaceUp", dealer.getHand().getCards().get(0));
        notifyObservers(new GameEvent(GameEventType.INITIAL_DEAL, "Initial cards have been dealt", initialData));
    }

    private void playerTurn() {
        PlayerAction action = inputSource.getPlayerAction();
        while (action == PlayerAction.HIT) {
            Card drawnCard = deck.draw();
            player.receiveCard(drawnCard);
            notifyObservers(new GameEvent(GameEventType.CARD_DEALT, player.getName() + " drew a card",
                    Map.of("card", drawnCard,
                            "fullHand", player.getHand().getCards(),
                            "participant", player.getName())));

            if (ruleSet.isBust(player.getHand())) {
                notifyObservers(new GameEvent(GameEventType.PLAYER_BUSTED, player.getName() + " has busted!",
                        Map.of("finalHand", player.getHand().getCards())));
                return;
            }

            action = inputSource.getPlayerAction();
        }
        notifyObservers(new GameEvent(GameEventType.PLAYER_STOOD, player.getName() + " has stood.", 
                Map.of("finalHand", player.getHand().getCards())));
    }

    private void dealerTurn() {
        notifyObservers(new GameEvent(GameEventType.DEALER_TURN_STARTED, "Dealer's turn has started.", 
                Map.of("dealerHand", dealer.getHand().getCards())));
        while (ruleSet.dealerShouldHit(dealer.getHand())) {
            Card drawnCard = deck.draw();
            dealer.receiveCard(drawnCard);
            notifyObservers(new GameEvent(GameEventType.CARD_DEALT, "Dealer drew a card",
                    Map.of("card", drawnCard,
                            "participant", dealer.getName())));
        }
        if (ruleSet.isBust(dealer.getHand())) {
            notifyObservers(new GameEvent(GameEventType.DEALER_BUSTED, "Dealer has busted!", 
                    Map.of("finalHand", dealer.getHand().getCards())));
        }
    }

    private void endRound() {
        RoundOutcome outcome = ruleSet.resolveOutcome(player.getHand(), dealer.getHand());
        String outcomeMessage = switch (outcome) {
            case PLAYER_WIN -> player.getName() + " wins!";
            case DEALER_WIN -> "Dealer wins!";
            case PUSH -> "It's a push!";
        };
        notifyObservers(new GameEvent(GameEventType.ROUND_ENDED, outcomeMessage));
    }

    @Override
    public void registerObserver(GameEventObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(GameEventObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(GameEvent event) {
        for (GameEventObserver observer : observers) {
            observer.onGameEvent(event);
        }
    }
}
