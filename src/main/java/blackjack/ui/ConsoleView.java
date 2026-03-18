package blackjack.ui;

import java.util.Scanner;

import blackjack.game.GameEvent;
import blackjack.game.GameEventObserver;
import blackjack.game.InputSource;
import blackjack.game.PlayerAction;

public class ConsoleView implements GameEventObserver, InputSource {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void onGameEvent(GameEvent event) {
        System.out.println(event.message());
        switch (event.type()) {
            case INITIAL_DEAL:
                System.out.println("Player's hand: " + event.data().get("playerCards"));
                System.out.println("Dealer's face-up card: " + event.data().get("dealerFaceUp"));
                break;
            case CARD_DEALT:
                System.out.println("Card dealt: " + event.data().get("card") + "\n" + "Hand now: " + event.data().get("fullHand"));
                break;
            case PLAYER_BUSTED:
                System.out.println("Player's final hand: " + event.data().get("finalHand"));
                break;
            case DEALER_BUSTED:
                System.out.println("Dealer's final hand: " + event.data().get("finalHand"));
                break;
            case PLAYER_STOOD:
                System.out.println("Player's final hand: " + event.data().get("finalHand"));
                break;
            case DEALER_TURN_STARTED:
                System.out.println("Dealer's hand: " + event.data().get("dealerHand"));
                break;
            default:
                break;
        }
    }

    @Override
    public PlayerAction getPlayerAction() {
        System.out.println("Please enter your action (hit/stand): \n");
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("hit")) {
                return PlayerAction.HIT;
            } else if (input.equals("stand")) {
                return PlayerAction.STAND;
            }
            System.out.println("Invalid action. Please enter 'hit' or 'stand': \n");
        }
    }
}
