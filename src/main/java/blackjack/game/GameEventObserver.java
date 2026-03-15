package blackjack.game;

public interface GameEventObserver {
    void onGameEvent(GameEvent event);
}
