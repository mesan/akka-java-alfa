package no.mesan.akka.linkFinder;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaParserMaster;
import no.mesan.akka.WikipediaScanRequest;
import no.mesan.akka.actors.FoundImage;

/**
 * Created by arnfinng on 22/06/2015.
 */
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
