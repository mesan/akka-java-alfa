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
                        .match(ParsePage.class, this::spawnGetHtmlActor)
                        .match(HTMLReceived.class, this::findSummary)
                        .matchAny(this::unhandled).build()
        );
    }

    private void spawnGetHtmlActor(final ParsePage pageParse) {
        final ActorRef htmlActor = context().actorOf(Props.create(GetHtmlActor.class));
        htmlActor.tell(pageParse, context().self());
    }

    private void findSummary(final HTMLReceived htmlReceived) {
        System.out.println(htmlReceived.getHtml());
    }
}
