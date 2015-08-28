package no.mesan.akka;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.actors.FoundImage;
import no.mesan.akka.actors.ImageFinder;
import no.mesan.akka.common.WikipediaArticleSummary;
import no.mesan.akka.linkFinder.LinkFinder;
import no.mesan.akka.summary.SummaryAsText;
import no.mesan.akka.summary.SummaryFinder;

import java.util.List;

public class WikipediaParserMaster extends AbstractActor {
    private WikipediaArticleSummary result;
    private ActorRef remoteCaller;
    private String url;

    public WikipediaParserMaster() {
        receive(ReceiveBuilder
                        .match(WikipediaScanRequest.class, this::handleScanRequest)
                        .match(FoundImage.class, this::addImage)
                        .match(SummaryAsText.class, this::addSummaryText)
                        .match(List.class, this::addLinkResult)
                        .matchAny(this::unhandled).build()
        );
    }

    private void returnArticleIfDone() {
        if (result.getImage() != null && !result.getLinkResults().isEmpty() && result.getSummary() != null) {
            System.out.println("sending result for query on " + url);
            remoteCaller.tell(result, ActorRef.noSender());
        }
    }

    private void addSummaryText(SummaryAsText summaryAsText) {
        result.setSummary(summaryAsText.getContents());
        returnArticleIfDone();
    }

    private void addLinkResult(List<WikipediaArticleSummary> wikipediaArticleSummary) {
        result.setLinkResults(wikipediaArticleSummary);
        returnArticleIfDone();
    }

    private void addImage(FoundImage image) {
        result.setImage(image.getImageUrl());
        returnArticleIfDone();
    }

    private void handleScanRequest(final WikipediaScanRequest wikipediaScanRequest) {
        System.out.println("Recieved new request to parse: " + wikipediaScanRequest.getContents());
        this.url = wikipediaScanRequest.getContents();
        result = new WikipediaArticleSummary(wikipediaScanRequest.getContents());
        context().actorOf(Props.create(ImageFinder.class)).tell(wikipediaScanRequest, self());
        context().actorOf(Props.create(LinkFinder.class)).tell(wikipediaScanRequest, self());
        context().actorOf(Props.create(SummaryFinder.class)).tell(wikipediaScanRequest, self());
        remoteCaller = wikipediaScanRequest.getRemoteCaller();
    }
}
