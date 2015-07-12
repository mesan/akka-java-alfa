package no.mesan.akka;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

public class WikipediaParser {

    private final ActorSystem actorSystem;

    public WikipediaParser() {
        actorSystem = ActorSystem.create(
                "WikipediaParser",
                ConfigFactory.load().getConfig("WikipediaParserConfig")
        );
        actorSystem.actorOf(Props.create(WikipediaParserMaster.class), "WikipediaParserMaster");
    }
}

