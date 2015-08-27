package no.mesan.akka.linkFinder;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaScanRequest;

import java.util.HashSet;
import java.util.Set;
public class HandledLinkHolder extends AbstractActor{
    private static int handledLinksAmount;

    public HandledLinkHolder() {
        receive(
                ReceiveBuilder
                        .match(Link.class, this::handleLink)
                        .matchAny(this::unhandled).build()
        );
    }

    private void handleLink(final Link foundLink) {
    }
}
