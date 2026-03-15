package blackjack.participants;

import java.util.ArrayList;
import java.util.List;

public class ParticipantFactory {
    public enum ParticipantType {
        PLAYER,
        DEALER
    }

    public Participant createParticipant(ParticipantType type, String name) {
        return switch (type) {
            case PLAYER -> new Player(name);
            case DEALER -> new Dealer();
        };
    }
    public List<Participant> createPlayers(List<String> playerNames) {
        List<Participant> participants = new ArrayList<>();
        for (String name : playerNames) {
            participants.add(new Player(name));
        }
        return participants;
    }
}
