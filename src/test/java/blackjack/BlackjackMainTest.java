package blackjack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlackjackMainTest {
    @Test
    void startupMessageMatchesProjectPurpose() {
        BlackjackMain application = new BlackjackMain();

        assertEquals("Hello World!", application.getStartupMessage());
    }
}
