package no.mesan.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.actors.ImageFinder;
import no.mesan.akka.common.RemoteWikipediaScanRequest;
import no.mesan.akka.common.WikipediaArticleSummary;
import no.mesan.akka.linkFinder.LinkFinder;
import no.mesan.akka.summary.SummaryAsText;
import no.mesan.akka.summary.SummaryFinder;

import java.awt.*;
import java.util.List;
public class WikipediaParserMaster extends AbstractActor {
    private WikipediaParseResult result;
    public WikipediaParserMaster() {
        receive(ReceiveBuilder
                        .match(WikipediaScanRequest.class, this::handleScanRequest)
                        .match(RemoteWikipediaScanRequest.class, this::handleRemoteScanRequest)
                        .match(Image.class, this::addImage)
                        .match(List.class, this::addLinkResult)
                        .match(SummaryAsText.class, this::addSummaryText)
                        .matchAny(this::unhandled).build()
        );
    }
    private void addSummaryText(SummaryAsText summaryAsText) {
        result.setSummary(summaryAsText.getContents());
    }
    private void addLinkResult(List<WikipediaParseResult> wikipediaParseResult) {
        result.setLinkResults(wikipediaParseResult);
    }

    private void addImage(Image image) {
        result.setImage(image);
        if(result.isDone())
            self().tell(result, self());
    }
    private void handleScanRequest(final WikipediaScanRequest wikipediaScanRequest) {
        result = new WikipediaParseResult(wikipediaScanRequest.getContents());
        System.out.println("Starter scan av url: " + wikipediaScanRequest.getContents());
        final ActorRef self = context().self();
        context().actorOf(Props.create(ImageFinder.class)).tell(wikipediaScanRequest, self);
        context().actorOf(Props.create(LinkFinder.class)).tell(wikipediaScanRequest, self);
        context().actorOf(Props.create(SummaryFinder.class)).tell(wikipediaScanRequest, self);
    }

    private void handleRemoteScanRequest(final RemoteWikipediaScanRequest remoteWikipediaScanRequest) {
        handleScanRequest(new WikipediaScanRequest(remoteWikipediaScanRequest.getUrl()));

        WikipediaArticleSummary article = new WikipediaArticleSummary();
        article.setTest("Hei og hå jeg er så glad!");

        sender().tell(article, ActorRef.noSender());
    }
}
