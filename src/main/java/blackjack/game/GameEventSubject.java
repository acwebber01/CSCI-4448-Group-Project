package blackjack.game;

public interface GameEventSubject {
    void registerObserver(GameEventObserver observer);
    void removeObserver(GameEventObserver observer);
}
