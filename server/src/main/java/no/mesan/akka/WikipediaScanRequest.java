package no.mesan.akka;

import no.mesan.akka.actors.ActorMessage;

public class WikipediaScanRequest extends ActorMessage<String> {
    private final int depth;

    public WikipediaScanRequest(final String url) {
        super(url);
        this.depth = 5;
    }

    public WikipediaScanRequest(final String url, final int depth){
        super(url);
        this.depth = depth;
    }
    public int getDepth() {
        return depth;
    }
}
