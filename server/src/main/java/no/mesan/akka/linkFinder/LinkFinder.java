package no.mesan.akka.linkFinder;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaScanRequest;
import org.jsoup.Jsoup;

import java.io.IOException;

public class LinkFinder extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    public LinkFinder() {
        receive(ReceiveBuilder
                        .match(WikipediaScanRequest.class, this::handleSourceRequest)
                        .matchAny(this::unhandled).build()
        );
    }

    private void handleSourceRequest(WikipediaScanRequest wikipediaScanRequest) throws IOException {
        log.debug("Finding links");
        Jsoup.connect(wikipediaScanRequest.getContents())
                .timeout(10000)
                .get()
                .select("a[href]")
                .stream()
                .map((url) -> url.attr("abs:href"))
                .filter(url -> url.contains("Wikipedia"))
                .map(Link::new)
                .forEach((foundLink) -> context().actorOf(Props.create(LinkHandler.class))
                        .tell(foundLink, context().self()));
    }

}
