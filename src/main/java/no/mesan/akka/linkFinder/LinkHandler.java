package no.mesan.akka.linkFinder;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaScanRequest;

public class LinkHandler extends AbstractActor{

    public LinkHandler() {
        receive(
                ReceiveBuilder
                        .match(Link.class, this::handleLink)
                        .matchAny(this::unhandled).build()
        );
    }

    private void handleLink(final Link foundLink) {
        WikipediaScanRequest request = new WikipediaScanRequest(foundLink.getUrl());
        System.out.println(request.getUrl());
       //Må fikse så bare wiki sider blir tatt med
       //Må ha liste over de vi har sett på? Eller?
       //Skal denne kalle master? hvordan blir det?

        //sender().tell(request, ActorRef.noSender());
    }
}
