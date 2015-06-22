package no.mesan.akka.summary;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author Knut Esten Melandsø Nekså
 */
public class GetSummaryAsHtml extends AbstractActor {
    public GetSummaryAsHtml() {
        receive(ReceiveBuilder
                        .match(ParsePage.class, this::getHtml)
                        .matchAny(this::unhandled).build()
        );
    }

    private void getHtml(final ParsePage parsePage) {
        sender().tell(new SummaryHtml(null), ActorRef.noSender());
    }
}
