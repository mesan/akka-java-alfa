package no.mesan.akka.linkFinder;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.common.WikipediaArticleSummary;
import no.mesan.akka.WikipediaParserMaster;
import no.mesan.akka.WikipediaScanRequest;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
public class LinkFinder extends AbstractActor {
    private int remainingDepth;
    private List<WikipediaArticleSummary> linkResults;
    private int numberOfLinks;
    public LinkFinder() {
        receive(ReceiveBuilder
                        .match(WikipediaScanRequest.class, this::handleSourceRequest)
                        .match(Link.class, this::handleFinishedLink)
                        .match(WikipediaArticleSummary.class, this::handleFinishedRequest)
                        .matchAny(this::unhandled).build()
        );
    }
    private void handleFinishedRequest(WikipediaArticleSummary wikipediaArticleSummary) {
        linkResults.add(wikipediaArticleSummary);
        numberOfLinks --;
        if(numberOfLinks == 0){
            context().sender().tell(linkResults, ActorRef.noSender());
        }
    }
    private void handleFinishedLink(Link foundLink) {
        if(remainingDepth != 0) {
            context()
                    .actorOf(Props.create(WikipediaParserMaster.class))
                    .tell(new WikipediaScanRequest(foundLink.getUrl(), remainingDepth - 1), context().self());
        }else{
           context().parent().tell(linkResults, ActorRef.noSender());
        }
    }

    private void handleSourceRequest(WikipediaScanRequest wikipediaScanRequest) throws IOException {
        remainingDepth = wikipediaScanRequest.getDepth();
        linkResults = new ArrayList<>();
        Jsoup.connect(wikipediaScanRequest.getContents())
                .timeout(10000)
                .get()
                .select("a[href]")
                .stream()
                .map((url) -> url.attr("abs:href"))
                .filter(url -> url.contains("wikipedia"))
                .filter(url -> !url.contains(wikipediaScanRequest.getContents()))
                .filter(url -> !url.contains("File:"))
                .map(Link::new).forEach((foundLink) -> {
                    context ()
                            .actorOf(Props.create(LinkHandler.class))
                            .tell(foundLink, context().self());
                    numberOfLinks++;
                });

        if (numberOfLinks == 0) {
            context().parent().tell(linkResults, ActorRef.noSender());
        }
    }
}
