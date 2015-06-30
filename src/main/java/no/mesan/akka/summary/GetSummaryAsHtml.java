package no.mesan.akka.summary;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class GetSummaryAsHtml extends AbstractActor {
    public GetSummaryAsHtml() {
        receive(ReceiveBuilder
                        .match(ParsePage.class, this::getHtml)
                        .matchAny(this::unhandled).build()
        );
    }

    private void getHtml(final ParsePage parsePage) throws IOException {
        final Element summary = Jsoup.connect(parsePage.getUrl())
                .timeout(10_000)
                .get()
                .select("p")
                .first();
        sender().tell(new SummaryHtml(summary), ActorRef.noSender());
    }
}
