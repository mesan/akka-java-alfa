package no.mesan.akka.summary;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author Knut Esten Melandsø Nekså
 */
public class SummaryActor extends AbstractActor {
    public SummaryActor() {
        receive(ReceiveBuilder
                        .match(ParsePage.class, this::findSummary)
                        .match(SummaryHtml.class, this::parseSummary)
                        .matchAny(this::unhandled).build()
        );
    }

    private void findSummary(final ParsePage pageParse) {
        final ActorRef htmlActor = context().actorOf(Props.create(GetSummaryAsHtml.class));
        htmlActor.tell(pageParse, context().self());
    }

    private void parseSummary(final SummaryHtml htmlReceived) {
        // her skal du starte min gode venn Daniel <3
        System.out.println(htmlReceived.getHtml());
    }
}
