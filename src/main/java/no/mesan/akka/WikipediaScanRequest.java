package no.mesan.akka;

import no.mesan.akka.actors.ActorMessage;

public class WikipediaScanRequest extends ActorMessage<String> {

    public WikipediaScanRequest(final String url) {
        super(url);
    }

}
