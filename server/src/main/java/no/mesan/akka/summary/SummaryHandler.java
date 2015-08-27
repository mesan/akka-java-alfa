package no.mesan.akka.summary;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import java.io.IOException;

public class SummaryHandler extends AbstractActor {
    public SummaryHandler() {
        receive(ReceiveBuilder
                        .match(SummaryAsHtml.class, this::parseHtmlToText)
                        .matchAny(this::unhandled).build()
        );
    }

    private void parseHtmlToText(final SummaryAsHtml summaryAsHtml) throws IOException {
        SummaryAsText summary = new SummaryAsText(summaryAsHtml.getHtml().text());
        sender().tell(summary, ActorRef.noSender());
    }
}
