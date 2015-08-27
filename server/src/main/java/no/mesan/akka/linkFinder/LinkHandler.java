package no.mesan.akka.linkFinder;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.agent.Agent;
import akka.dispatch.ExecutionContexts;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaScanRequest;
import scala.concurrent.ExecutionContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class LinkHandler extends AbstractActor{
    public static int handledLinks;
    public LinkHandler() {
        receive(
                ReceiveBuilder
                        .match(Link.class, this::handleLink)
                        .matchAny(this::unhandled).build()
        );
    }

    private void handleLink(final Link foundLink) {
        WikipediaScanRequest request = new WikipediaScanRequest(foundLink.getUrl());
        LinkHandler.handledLinks++;
        if(handledLinks < 100) {
            sender().tell(request, ActorRef.noSender());
        }
    }
}
