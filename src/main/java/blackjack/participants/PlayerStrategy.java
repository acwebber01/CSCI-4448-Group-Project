package blackjack.participants;

public interface PlayerStrategy {
    boolean shouldHit(Participant participant, Participant dealer);
}