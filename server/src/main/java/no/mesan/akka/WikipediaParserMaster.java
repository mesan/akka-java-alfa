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
    private WikipediaArticleSummary result;
    public WikipediaParserMaster() {
        receive(ReceiveBuilder
                        .match(WikipediaScanRequest.class, this::handleScanRequest)
                        .match(RemoteWikipediaScanRequest.class, this::handleRemoteScanRequest)
                        .match(Image.class, this::addImage)
                        .match(SummaryAsText.class, this::addSummaryText)
                        .match(WikipediaArticleSummary.class, this::sendResult)
                        .match(List.class, this::addLinkResult)
                        .matchAny(this::unhandled).build()
        );
    }
    private void sendResult(WikipediaArticleSummary wikipediaArticleSummary) {
        sender().tell(result, ActorRef.noSender());
    }
    private void addSummaryText(SummaryAsText summaryAsText) {
        result.setSummary(summaryAsText.getContents());
        if(result.isDone())
            self().tell(result, self());
    }
    private void addLinkResult(List<WikipediaArticleSummary> wikipediaArticleSummary) {
        result.setLinkResults(wikipediaArticleSummary);
        if(result.isDone())
            self().tell(result, self());
    }

    private void addImage(Image image) {
        result.setImage(image);
        if(result.isDone())
            self().tell(result, self());
    }
    private void handleScanRequest(final WikipediaScanRequest wikipediaScanRequest) {
        result = new WikipediaArticleSummary(wikipediaScanRequest.getContents());
        System.out.println("Starter scan av url: " + wikipediaScanRequest.getContents());
        final ActorRef self = context().self();
        context().actorOf(Props.create(ImageFinder.class)).tell(wikipediaScanRequest, self);
        context().actorOf(Props.create(LinkFinder.class)).tell(wikipediaScanRequest, self);
        context().actorOf(Props.create(SummaryFinder.class)).tell(wikipediaScanRequest, self);
    }

    private void handleRemoteScanRequest(final RemoteWikipediaScanRequest request) {
        handleScanRequest(new WikipediaScanRequest(request.getUrl(), request.getDepth()));
    }
}
