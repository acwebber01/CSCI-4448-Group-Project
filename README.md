# Blackjack

## Design Patterns
Builder - The [RuleConfig](./src/main/java/blackjack/rules/RuleConfig.java) class is built using a builder, allowing for precise deviation from standard blackjack rules when starting a game; this will be particularly useful when adding on more features to make a Balatro-like Blackjack game. 

Factory - The [DeckFactory](./src/main/java/blackjack/cards/DeckFactory.java) class is a simple factory which handles the creation of the Deck object. It stands to be convenient by centralizing deck creation.

Observer - Currently the most useful pattern, the [GameEventSubject](./src/main/java/blackjack/game/GameEventSubject.java) notifies all [GameEventObservers](./src/main/java/blackjack/game/GameEventObserver.java) with in a push-observer style by sending a [GameEvent](./src/main/java/blackjack/game/GameEvent.java) object alongside the current [GameState](./src/main/java/blackjack/game/GameState.java). Sends to a [logging utility](./src/main/java/blackjack/game/GameLogger.java) and the [ConsoleView](./src/main/java/blackjack/ui/ConsoleView.java) UI, so the game code doesn't actually know about either of those.