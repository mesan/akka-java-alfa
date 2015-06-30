package no.mesan.akka.summary;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class SummaryActorTest {

    public static void main(final String[] args) {
        new SummaryActorTest().findSummary();
    }

    public void findSummary() {
        final ActorSystem actorSystem = ActorSystem.create("summary");
        final ActorRef master = actorSystem.actorOf(Props.create(SummaryActor.class), "master");
        master.tell(new ParsePage("https://en.wikipedia.org/wiki/Akka_(toolkit)"), ActorRef.noSender());
    }
}
