package blackjack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlackjackMain {
    private static final Logger logger = LoggerFactory.getLogger(BlackjackMain.class);

    public String getStartupMessage() {
        return "Hello World!";
    }

    public void run() {
        logger.info(getStartupMessage());
    }

    public static void main(String[] args) {
        new BlackjackMain().run();
    }
}
