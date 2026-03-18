package blackjack.game;

public interface InputSource {
    PlayerAction getPlayerAction();
    boolean getPlayAgainChoice();
}
