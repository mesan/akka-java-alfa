package no.mesan.akka.summary;

import akka.actor.AbstractActor;
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

        System.out.println(summary.getSummary());
        // Send det et sted
//        final ActorRef htmlActor = context().actorOf(Props.create(WikipediaParserMaster.class));
//        htmlActor.tell(summary, ActorRef.noSender());
    }
}
