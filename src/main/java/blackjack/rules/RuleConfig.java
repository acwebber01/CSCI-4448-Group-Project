package blackjack.rules;

import blackjack.cards.Rank;

import java.util.EnumMap;
import java.util.Map;

public final class RuleConfig {
    private final int blackjackValue;
    private final int bustValue;
    private final int dealerStandValue;
    private final boolean dealerHitsSoft;
    private final EnumMap<Rank, Integer> cardValues;

    private RuleConfig(Builder builder) {
        this.blackjackValue = builder.blackjackValue;
        this.bustValue = builder.bustValue;
        this.dealerStandValue = builder.dealerStandValue;
        this.dealerHitsSoft = builder.dealerHitsSoft;
        this.cardValues = new EnumMap<>(builder.cardValues);
    }

    public static Builder builder() {
        return new Builder();
    }

    public int blackjackValue() {
        return blackjackValue;
    }

    public int bustValue() {
        return bustValue;
    }

    public int dealerStandValue() {
        return dealerStandValue;
    }

    public boolean dealerHitsSoft() {
        return dealerHitsSoft;
    }

    public int cardValue(Rank rank) {
        Integer value = cardValues.get(rank);
        if (value == null) {
            throw new IllegalArgumentException("No configured card value for rank: " + rank);
        }
        return value;
    }

    public Map<Rank, Integer> cardValues() {
        return Map.copyOf(cardValues);
    }

    public static final class Builder {
        private int blackjackValue = 21;
        private int bustValue = 21;
        private int dealerStandValue = 17;
        private boolean dealerHitsSoft = false;
        private final EnumMap<Rank, Integer> cardValues = defaultCardValues();

        private Builder() {
        }

        public Builder blackjackValue(int blackjackValue) {
            this.blackjackValue = blackjackValue;
            return this;
        }

        public Builder bustValue(int bustValue) {
            this.bustValue = bustValue;
            return this;
        }

        public Builder dealerStandValue(int dealerStandValue) {
            this.dealerStandValue = dealerStandValue;
            return this;
        }

        public Builder dealerHitsSoft(boolean dealerHitsSoft) {
            this.dealerHitsSoft = dealerHitsSoft;
            return this;
        }

        public Builder cardValue(Rank rank, int value) {
            cardValues.put(rank, value);
            return this;
        }

        public Builder cardValues(Map<Rank, Integer> values) {
            for (Map.Entry<Rank, Integer> entry : values.entrySet()) {
                cardValue(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public RuleConfig build() {
            validate();
            return new RuleConfig(this);
        }

        private void validate() {
            if (blackjackValue <= 0) {
                throw new IllegalArgumentException("Blackjack value must be positive");
            }

            if (bustValue < blackjackValue) {
                throw new IllegalArgumentException("Bust value must be at least the blackjack value");
            }

            if (dealerStandValue <= 0) {
                throw new IllegalArgumentException("Dealer stand value must be positive");
            }

            if (cardValues.size() != Rank.values().length) {
                throw new IllegalArgumentException("Card values must be configured for every rank");
            }

            for (Map.Entry<Rank, Integer> entry : cardValues.entrySet()) {
                if (entry.getValue() <= 0) {
                    throw new IllegalArgumentException("Card values must be positive for rank: " + entry.getKey());
                }
            }
        }

        private static EnumMap<Rank, Integer> defaultCardValues() {
            EnumMap<Rank, Integer> values = new EnumMap<>(Rank.class);
            values.put(Rank.ACE, 11);
            values.put(Rank.TWO, 2);
            values.put(Rank.THREE, 3);
            values.put(Rank.FOUR, 4);
            values.put(Rank.FIVE, 5);
            values.put(Rank.SIX, 6);
            values.put(Rank.SEVEN, 7);
            values.put(Rank.EIGHT, 8);
            values.put(Rank.NINE, 9);
            values.put(Rank.TEN, 10);
            values.put(Rank.JACK, 10);
            values.put(Rank.QUEEN, 10);
            values.put(Rank.KING, 10);
            return values;
        }
    }
}
