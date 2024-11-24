package patterns.polling;

import kickstart.KnownMethods;
import kickstart.RequestMessage;

import java.util.UUID;

public class PollRequest<T> extends RequestMessage {
    private final UUID pollID;

    public PollRequest(KnownMethods method, T requestData, UUID uuid) {
        super(method, requestData, uuid);
        this.pollID = UUID.randomUUID();
    }

    public UUID getPollID() {
        return this.pollID;
    }
}
