package blackjack.game;

import java.util.Map;

public record GameEvent(GameEventType type, String message, Map<String, Object> data) {
    public GameEvent(GameEventType type, String message) {
        this(type, message, null);
    }
    
}
