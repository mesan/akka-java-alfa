package no.mesan.akka.summary;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaScanRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class SummaryFinder extends AbstractActor {
    public SummaryFinder() {
        receive(ReceiveBuilder
                        .match(WikipediaScanRequest.class, this::findSummary)
                        .match(SummaryAsText.class, this::finishedSummary)
                        .matchAny(this::unhandled).build()
        );
    }

    private void findSummary(final WikipediaScanRequest scanRequest) throws IOException {
        try{
            final Element summary = Jsoup.connect(scanRequest.getContents())
                    .timeout(10_000)
                    .get()
                    .select("#mw-content-text")
                    .select("p")
                    .first();
            final ActorRef htmlActor = context().actorOf(Props.create(SummaryHandler.class));
            htmlActor.tell(new SummaryAsHtml(summary), self());
        }
        catch (Exception e){
            context().parent().tell(new SummaryAsText("No summary"), ActorRef.noSender());
        }

    }

    private void finishedSummary(final SummaryAsText summary) {
        context().parent().tell(summary, ActorRef.noSender());
    }
}
