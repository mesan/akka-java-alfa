package no.mesan.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class WikipediaParserTest {

    private final String wikipediaUrl = "https://en.wikipedia.org/wiki/Akka_(toolkit)";

    public static void main(final String[] args) {
        new WikipediaParserTest().parseWikipedia();
    }

    public void parseWikipedia() {
        final ActorSystem actorSystem = ActorSystem.create("WikipediaParser");
        final ActorRef master = actorSystem.actorOf(Props.create(WikipediaParserMaster.class), "master");
        master.tell(new WikipediaScanRequest(wikipediaUrl), ActorRef.noSender());
    }
}
