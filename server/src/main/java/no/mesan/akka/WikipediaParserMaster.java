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
        if (result.getImage() != null && result.getLinkResults().isEmpty() && result.getSummary() != null) {
            remoteCaller.tell(result, ActorRef.noSender());
        }
    }

    private void addSummaryText(SummaryAsText summaryAsText) {
        System.out.println("summary is done");
        result.setSummary(summaryAsText.getContents());
        returnArticleIfDone();
    }

    private void addLinkResult(List<WikipediaArticleSummary> wikipediaArticleSummary) {
        System.out.println("link is done");
        result.setLinkResults(wikipediaArticleSummary);
        returnArticleIfDone();
    }

    private void addImage(FoundImage image) {
        System.out.println("image is done");
        result.setImage(image.getImageUrl());
        returnArticleIfDone();
    }

    private void handleScanRequest(final WikipediaScanRequest wikipediaScanRequest) {
        System.out.println(wikipediaScanRequest.getContents());
        result = new WikipediaArticleSummary(wikipediaScanRequest.getContents());
        context().actorOf(Props.create(ImageFinder.class)).tell(wikipediaScanRequest, self());
        context().actorOf(Props.create(LinkFinder.class)).tell(wikipediaScanRequest, self());
        context().actorOf(Props.create(SummaryFinder.class)).tell(wikipediaScanRequest, self());
        remoteCaller = wikipediaScanRequest.getRemoteCaller();
    }
}
