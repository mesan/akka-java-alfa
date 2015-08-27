package no.mesan.akka.linkFinder;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaParseResult;
import no.mesan.akka.WikipediaParserMaster;
import no.mesan.akka.WikipediaScanRequest;
import org.jsoup.Jsoup;

import java.io.IOException;

public class LinkFinder extends AbstractActor {
    private int remainingDepth;
    public LinkFinder() {
        receive(ReceiveBuilder
                        .match(WikipediaScanRequest.class, this::handleSourceRequest)
                        .match(Link.class, this::handleFinishedRequest)
                        .matchAny(this::unhandled).build()
        );
    }
    private void handleFinishedRequest(Link foundLink) {
        if(remainingDepth != 0) {
            context()
                    .actorOf(Props.create(WikipediaParserMaster.class))
                    .tell(new WikipediaScanRequest(foundLink.getUrl(), remainingDepth - 1), context().self());
        }
    }
    private void handleSourceRequest(WikipediaScanRequest wikipediaScanRequest) throws IOException {
        System.out.println(wikipediaScanRequest.getContents());
        remainingDepth = wikipediaScanRequest.getDepth();
        Jsoup.connect(wikipediaScanRequest.getContents())
                .timeout(10000)
                .get()
                .select("a[href]")
                .stream()
                .map((url) -> url.attr("abs:href"))
                .filter(url -> url.contains("wikipedia"))
                .filter(url -> !url.contains(wikipediaScanRequest.getContents()))
                .filter(url -> !url.contains("File:"))
                .map(Link::new)
                .forEach((foundLink) -> context ()
                        .actorOf(Props.create(LinkHandler.class))
                        .tell(foundLink, context().self()));

    }

}
