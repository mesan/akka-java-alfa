package no.mesan.akka;

import akka.actor.ActorRef;
import no.mesan.akka.actors.ActorMessage;

public class WikipediaScanRequest extends ActorMessage<String> {
    private final int depth;
    private final ActorRef remoteCaller;
    public WikipediaScanRequest(final String url, ActorRef remoteCaller) {
        super(url);
        this.remoteCaller = remoteCaller;
        this.depth = 5;
    }
    public WikipediaScanRequest(String url, int depth, ActorRef remoteCaller) {
        super(url);
        this.depth = depth;
        this.remoteCaller = remoteCaller;
    }
    public int getDepth() {
        return depth;
    }
    public ActorRef getRemoteCaller() {
        return remoteCaller;
    }
}
