package no.mesan.akka.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;
import no.mesan.akka.common.RemoteWikipediaScanRequest;

public class WikipediaParserClient {

    private final ActorRef remoteActor;

    public WikipediaParserClient() {
        final ActorSystem actorSystem = ActorSystem.create(
                "WikipediaParserClient",
                ConfigFactory.load().getConfig("WikipediaParserClientConfig")
        );
        remoteActor = actorSystem.actorFor(
                "akka.tcp://WikipediaParser@127.0.0.1:2552/user/WikipediaParserMaster"
        );
    }

    public void scan(final RemoteWikipediaScanRequest wikipediaScanRequest) {
        remoteActor.tell(wikipediaScanRequest, ActorRef.noSender());
    }
}
