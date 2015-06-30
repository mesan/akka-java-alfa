package no.mesan.akka.summary;

import no.mesan.akka.actors.ActorMessage;

public class ParsePage extends ActorMessage<String> {
    public ParsePage(String url) {
        super(url);
    }

    public String getUrl() {
        return getContents();
    }
}
