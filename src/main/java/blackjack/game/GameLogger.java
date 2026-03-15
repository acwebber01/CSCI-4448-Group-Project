package blackjack.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameLogger implements GameEventObserver {
    private static final Logger logger = LoggerFactory.getLogger(GameLogger.class);

    @Override
    public void onGameEvent(GameEvent event) {
        logger.info("Event: " + event.type() + " - " + event.message());
    } 
}
