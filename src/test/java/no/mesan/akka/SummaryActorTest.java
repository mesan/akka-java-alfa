package no.mesan.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import junit.framework.TestCase;

/**
 * @author Knut Esten Melandsø Nekså
 */
public class SummaryActorTest extends TestCase {

    public static void main(final String[] args) {
        new SummaryActorTest().findSummary();
    }

    public void findSummary() {
        final ActorSystem actorSystem = ActorSystem.create("summary");
        final ActorRef master = actorSystem.actorOf(Props.create(SummaryActor.class), "master");
        master.tell("https://en.wikipedia.org/wiki/Akka_(toolkit)", ActorRef.noSender());
    }
}