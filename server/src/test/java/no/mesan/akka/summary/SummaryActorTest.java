package no.mesan.akka.summary;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import no.mesan.akka.WikipediaScanRequest;

public class SummaryActorTest {

    public static void main(final String[] args) {
        new SummaryActorTest().findSummary();
    }

    public void findSummary() {
        final ActorSystem actorSystem = ActorSystem.create("summary");
        final ActorRef master = actorSystem.actorOf(Props.create(SummaryFinder.class), "master");
        master.tell(new WikipediaScanRequest("https://en.wikipedia.org/wiki/Akka_(toolkit)"), ActorRef.noSender());
    }
}
