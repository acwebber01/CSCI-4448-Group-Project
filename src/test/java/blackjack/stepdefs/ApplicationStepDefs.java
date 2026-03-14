package blackjack.stepdefs;

import blackjack.BlackjackMain;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationStepDefs {
    private String startupMessage;

    @When("the blackjack application starts")
    public void theBlackjackApplicationStarts() {
        BlackjackMain application = new BlackjackMain();
        startupMessage = application.getStartupMessage();
    }

    @Then("the startup message is {string}")
    public void theStartupMessageIs(String expectedMessage) {
        assertEquals(expectedMessage, startupMessage);
    }
}
