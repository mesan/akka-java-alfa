package no.mesan.akka.linkFinder;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaScanRequest;
import no.mesan.akka.actors.FoundImage;
import no.mesan.akka.actors.ImageHandler;
import org.jsoup.Jsoup;
import java.util.stream.Collectors;
import java.io.IOException;

/**
 * Created by arnfinng on 22/06/2015.
 */
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
        Jsoup.connect(wikipediaScanRequest.getUrl())
                .timeout(10000)
                .get()
                .select("a[href]")
                .stream()
                .map((url) -> url.attr("abs:href"))
                .map(Link::new)
                .forEach((foundLink) -> context().actorOf(Props.create(LinkHandler.class))
                        .tell(foundLink, context().self()));
    }

}
