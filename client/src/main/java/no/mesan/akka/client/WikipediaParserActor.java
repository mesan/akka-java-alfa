package no.mesan.akka.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.common.RemoteWikipediaScanRequest;
import no.mesan.akka.common.WikipediaArticleSummary;

/**
 * @author Knut Esten Melandsø Nekså
 */
public class WikipediaParserActor extends AbstractActor {
    private final ActorRef remoteActor;

    public WikipediaParserActor() {
        remoteActor = context().actorFor(
                "akka.tcp://WikipediaParser@127.0.0.1:2552/user/WikipediaParserMaster"
        );
        receive(ReceiveBuilder
                .match(WikipediaArticleSummary.class, this::handleResponse)
                .match(RemoteWikipediaScanRequest.class, this::scan)
                .build());
    }

    private void handleResponse(WikipediaArticleSummary wikipediaArticleSummary) {
        System.out.println(wikipediaArticleSummary.getTest());
    }

    private void scan(RemoteWikipediaScanRequest wikipediaScanRequest) {
        remoteActor.tell(wikipediaScanRequest, self());
    }
}
