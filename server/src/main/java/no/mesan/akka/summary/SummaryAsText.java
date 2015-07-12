package no.mesan.akka.summary;

import no.mesan.akka.actors.ActorMessage;

/**
 * @author Knut Esten Melandsø Nekså
 */
public class SummaryAsText extends ActorMessage<String> {
    public SummaryAsText(String contents) {
        super(contents);
    }

    public String getSummary() {
        return getContents();
    }
}
