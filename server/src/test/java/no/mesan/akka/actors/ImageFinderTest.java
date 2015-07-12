package no.mesan.akka.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import no.mesan.akka.WikipediaScanRequest;

public class ImageFinderTest {

    public static void main(String[] args) throws InterruptedException {
        final WikipediaScanRequest scanRequest =
                new WikipediaScanRequest("https://en.wikipedia.org/wiki/Akka_(toolkit)");

        final Props props = Props.create(ImageFinder.class);
        final ActorSystem system = ActorSystem.create();
        TestActorRef<ImageFinder> testActorRef = TestActorRef.create(system, props, "ImageFinderTest");
        testActorRef.tell(scanRequest, ActorRef.noSender());
        Thread.sleep(5000);
        system.shutdown();
    }
}