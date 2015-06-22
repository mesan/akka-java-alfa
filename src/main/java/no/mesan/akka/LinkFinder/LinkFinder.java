package no.mesan.akka.linkFinder;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaScanRequest;
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
        System.out.println(Jsoup.connect(wikipediaScanRequest.getUrl())
                .timeout(10000)
                .get()
                .select("a[href]")
                .stream()
                .map((image) -> image.attr("abs:href"))
                .collect(Collectors.joining("\n")));
    }

}
