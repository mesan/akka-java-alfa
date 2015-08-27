package no.mesan.akka.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import no.mesan.akka.common.RemoteWikipediaScanRequest;

public class WikipediaParserClient {
    private final ActorSystem actorSystem;

    public WikipediaParserClient() {
        actorSystem = ActorSystem.create(
                "WikipediaParserClient",
                ConfigFactory.load().getConfig("WikipediaParserClientConfig")
        );
    }

    public void scan(final RemoteWikipediaScanRequest wikipediaScanRequest) {
        actorSystem.actorOf(Props.create(WikipediaParserActor.class)).tell(wikipediaScanRequest, ActorRef.noSender());
    }
}
