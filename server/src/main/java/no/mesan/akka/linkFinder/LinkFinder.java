package no.mesan.akka.linkFinder;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaParserMaster;
import no.mesan.akka.WikipediaScanRequest;
import no.mesan.akka.common.WikipediaArticleSummary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class LinkFinder extends AbstractActor {
    private int remainingDepth;
    private List<WikipediaArticleSummary> linkResults = new ArrayList<>();
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
            context().parent().tell(linkResults, ActorRef.noSender());
        }
    }
    private void handleFinishedLink(Link foundLink) {
        if(remainingDepth != 0) {
            context()
                    .actorOf(Props.create(WikipediaParserMaster.class))
                    .tell(new WikipediaScanRequest(foundLink.getUrl(), remainingDepth - 1, self()), context().self());
        }else{
            linkResults.add(new WikipediaArticleSummary(foundLink.getUrl()));
            context().parent().tell(linkResults, ActorRef.noSender());
        }
    }

    private void handleSourceRequest(WikipediaScanRequest wikipediaScanRequest) throws IOException {
        remainingDepth = wikipediaScanRequest.getDepth();
        try {
            String pageTitle = wikipediaScanRequest.getContents().substring("https://en.wikipedia.org/wiki/".length());
            final Document document = Jsoup.connect(wikipediaScanRequest.getContents())
                    .timeout(10000)
                    .get();
            document
                    .select("#mw-content-text")
                    .first()
                    .select("a[href]")
                    .stream()
                    .map((url) -> url.attr("abs:href"))
                    .filter(url -> url.contains("wikipedia"))
                    .filter(url -> !url.contains("Wikipedia:"))
                    .filter(url -> !url.contains("Help:"))
                    .filter(url -> !url.contains("Talk:"))
                    .filter(url -> !url.contains("Template_talk:"))
                    .filter(url -> !url.contains("&action=edit"))
                    .filter(url -> !url.contains(pageTitle))
                    .filter(url -> !url.contains("Category:"))
                    .filter(url -> !url.contains("Template:"))
                    .filter(url -> !url.contains("Special:"))
                    .filter(url -> !url.contains(wikipediaScanRequest.getContents()))
                    .filter(url -> !url.contains("File:"))
                    .map(Link::new).forEach((foundLink) -> {
                context()
                        .actorOf(Props.create(LinkHandler.class))
                        .tell(foundLink, context().self());
                numberOfLinks++;
            });
        }catch (Exception e){
            linkResults.add(new WikipediaArticleSummary(wikipediaScanRequest.getContents()));
            context().parent().tell(linkResults, ActorRef.noSender());
        }
        if (numberOfLinks == 0) {
            linkResults.add(new WikipediaArticleSummary(wikipediaScanRequest.getContents()));
            context().parent().tell(linkResults, ActorRef.noSender());
        }
    }
}
