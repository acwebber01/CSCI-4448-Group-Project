package blackjack.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import blackjack.cards.Deck;
import blackjack.cards.DeckFactory;
import blackjack.participants.*;
import blackjack.rules.RuleConfig;
import blackjack.rules.RuleSet;
import blackjack.rules.StandardRuleSet;

public class GameControllerTest {
    public class MockGameEventObserver implements GameEventObserver {
        private List<GameEvent> events = new ArrayList<>();

        @Override
        public void onGameEvent(GameEvent event) {
            this.events.add(event);
        }

        public List<GameEvent> getEvents() {
            return events;
        }

        public GameEvent getLastEvent() {
            return events.get(events.size() - 1);
        }

        public boolean hasEventOfType(GameEventType type) {
            return events.stream().anyMatch(e -> e.type() == type);
        }
    }

    public class MockInputSource implements InputSource {
        private final List<PlayerAction> actions = new ArrayList<>();
        private int index = 0;

        public void setActions(PlayerAction... actions) {
            this.actions.clear();
            this.actions.addAll(List.of(actions));
            this.index = 0;
        }

        @Override
        public PlayerAction getPlayerAction() {
            return actions.get(index++);
        }
    }

    DeckFactory deckFactory = new DeckFactory();
    Deck deck;
    RuleSet ruleSet;
    Player player;
    Dealer dealer;
    MockInputSource inputSource;
    GameController controller;
    MockGameEventObserver observer;

    @BeforeEach
    void setup() {
        RuleConfig.Builder builder = RuleConfig.builder();
        this.deck = deckFactory.createStandardDeck();
        this.ruleSet = new StandardRuleSet(builder.build());
        this.player = new Player();
        this.dealer = new Dealer();
        this.inputSource = new MockInputSource();
        this.controller = new GameController(deck, ruleSet, player, dealer, inputSource);
        this.observer = new MockGameEventObserver();
        controller.registerObserver(observer);
    }

    @AfterEach
    void tearDown() {
        controller.removeObserver(observer);
    }

    @Test
    void testInitialDealGivesTwoCardsEach() {
        inputSource.setActions(PlayerAction.STAND);
        controller.playRound();

        assertEquals(2, player.getHand().size());
        assertEquals(2, dealer.getHand().size());
        assertTrue(observer.hasEventOfType(GameEventType.INITIAL_DEAL));
    }

    @Test
    void testPlayerHitReceivesCard() {
        inputSource.setActions(PlayerAction.HIT, PlayerAction.STAND);
        controller.playRound();

        // 2 from deal + 1 from hit
        assertEquals(3, player.getHand().size());
        assertTrue(observer.hasEventOfType(GameEventType.CARD_DEALT));
    }

    @Test
    void testPlayerStandTriggersEvent() {
        inputSource.setActions(PlayerAction.STAND);
        controller.playRound();

        assertEquals(2, player.getHand().size());
        assertTrue(observer.hasEventOfType(GameEventType.PLAYER_STOOD));
    }

    @Test
    void testRoundAlwaysEnds() {
        inputSource.setActions(PlayerAction.STAND);
        controller.playRound();

        assertEquals(observer.getLastEvent().type(), GameEventType.ROUND_ENDED);
    }
}
