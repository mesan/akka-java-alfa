package no.mesan.akka.linkFinder;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import no.mesan.akka.WikipediaScanRequest;
import no.mesan.akka.actors.ImageFinder;

public class LinkFinderTest {

    public static void main(String[] args) throws InterruptedException {
        final WikipediaScanRequest scanRequest =
                new WikipediaScanRequest("https://en.wikipedia.org/wiki/Akka_(toolkit)");

        final Props props = Props.create(LinkFinder.class);
        final ActorSystem system = ActorSystem.create();
        TestActorRef<LinkFinder> testActorRef = TestActorRef.create(system, props, "LinkFinderTest");
        testActorRef.tell(scanRequest, ActorRef.noSender());
    }
}