package blackjack.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blackjack.cards.Deck;
import blackjack.cards.DeckFactory;
import blackjack.participants.*;
import blackjack.participants.ParticipantFactory.ParticipantType;
import blackjack.rules.RuleConfig;
import blackjack.rules.RuleSet;
import blackjack.rules.StandardRuleSet;

public class GameControllerTest {
    public class MockGameEventObserver implements GameEventObserver {
        private GameEvent lastEvent;

        @Override
        public void onGameEvent(GameEvent event) {
            this.lastEvent = event;
        }

        public GameEvent getLastEvent() {
            return lastEvent;
        }
    }

    DeckFactory deckFactory = new DeckFactory();
    ParticipantFactory participantFactory = new ParticipantFactory();
    Deck deck;
    RuleSet ruleSet;
    Participant player;
    Participant dealer;
    GameController controller;
    MockGameEventObserver observer;

    @BeforeEach
    void setup() {
        RuleConfig.Builder builder = RuleConfig.builder();
        this.deck = deckFactory.createStandardDeck();
        this.ruleSet = new StandardRuleSet(builder.build());
        this.player = participantFactory.createParticipant(ParticipantType.PLAYER, "Player 1");
        this.dealer = participantFactory.createParticipant(ParticipantType.DEALER, "Dealer");
        this.controller = new GameController(deck, ruleSet, (Player) player, (Dealer) dealer);
        this.observer = new MockGameEventObserver();
        controller.registerObserver(observer);
    }

    @AfterEach
    void tearDown() {
        controller.removeObserver(observer);
    }

    @Test
    void testInitialDealEvent() {
        controller.dealInitialCards();

        // Verify that the participants' hand has 2 cards under the standard rules
        assertEquals(2, player.getHand().size());
        assertEquals(2, dealer.getHand().size());

        // Verify that the observer received the INITIAL_DEAL event
        assertNotNull(observer.getLastEvent());
        assertEquals(observer.getLastEvent().type(), GameEventType.INITIAL_DEAL);
    }

    @Test
    void testPlayerHitEvent() {
        controller.playerHit();

        // Verify that the player's hand has a card after hitting
        assertEquals(1, player.getHand().size());

        // Verify that the observer received the CARD_DEALT event
        assertNotNull(observer.getLastEvent());
        assertEquals(observer.getLastEvent().type(), GameEventType.CARD_DEALT);
    }

    @Test
    void testPlayerStandEvent() {
        controller.playerStand();

        // Verify that the player didn't receive any more cards after standing
        assertEquals(0, player.getHand().size());

        // Verify that the dealer's hand has cards after the player stands
        assertTrue(dealer.getHand().size() > 0);

        // We can't test the observer getting the PLAYER_STOOD event yet
    }

    @Test
    void testRoundEndedEvent() {
        // Simulate a scenario where the player busts immediately
        while (!ruleSet.isBust(player.getHand())) {
            controller.playerHit();
        }

        // Verify that the observer received the ROUND_ENDED event after the player busts
        controller.playerHit(); // Trigger another hit to ensure endRound is called
        assertEquals(observer.getLastEvent().type(), GameEventType.ROUND_ENDED);
    }
}
