package no.mesan.akka.summary;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author Knut Esten Melandsø Nekså
 */
public class GetHtmlActor extends AbstractActor {
    public GetHtmlActor() {
        receive(ReceiveBuilder
                        .match(ParsePage.class, this::getHtml)
                        .matchAny(this::unhandled).build()
        );
    }

    private void getHtml(final ParsePage parsePage) {
        sender().tell(new HTMLReceived("hei"), ActorRef.noSender());
    }
}
