package blackjack.ui;

import java.util.Scanner;

import blackjack.game.GameEvent;
import blackjack.game.GameEventObserver;
import blackjack.game.GameState;
import blackjack.game.InputSource;
import blackjack.game.PlayerAction;

public class ConsoleView implements GameEventObserver, InputSource {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public boolean getPlayAgainChoice() {
        System.out.print("Do you want to play again? (yes/no): ");
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                return true;
            } else if (input.equals("no")) {
                return false;
            }
            System.out.print("Invalid choice. Enter 'yes' or 'no': ");
        }
    }

    @Override
    public void onGameEvent(GameEvent event, GameState state) {
        switch (event.type()) {
            case INITIAL_DEAL -> {
                System.out.println("Your hand: " + state.getPlayerCards());
                System.out.println("Dealer shows: " + state.getDealerFaceUp());
            }
            case CARD_DEALT -> System.out.println(event.message());
            case PLAYER_BUSTED -> {
                System.out.println(event.message());
                System.out.println("Your final hand: " + state.getPlayerCards());
            }
            case PLAYER_STOOD -> System.out.println(event.message());
            case DEALER_TURN_STARTED -> System.out.println("Dealer reveals: " + state.getDealerCards());
            case DEALER_BUSTED -> System.out.println(event.message());
            case ROUND_ENDED -> System.out.println(event.message());
        }
    }

    @Override
    public PlayerAction getPlayerAction() {
        System.out.print("Hit or stand? ");
        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("hit")) {
                return PlayerAction.HIT;
            } else if (input.equals("stand")) {
                return PlayerAction.STAND;
            }
            System.out.print("Invalid action. Enter 'hit' or 'stand': ");
        }
    }
}
