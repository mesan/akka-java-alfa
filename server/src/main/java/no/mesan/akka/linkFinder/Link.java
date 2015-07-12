package no.mesan.akka.linkFinder;

import no.mesan.akka.actors.ActorMessage;

public class Link extends ActorMessage<String> {

    public Link(final String url) {
        super(url);
    }

    public String getUrl() {
        return getContents();
    }
}
