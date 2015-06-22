package no.mesan.akka.LinkFinder;

import akka.actor.AbstractActor;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaScanRequest;

/**
 * Created by arnfinng on 22/06/2015.
 */
public class LinkFinderActor extends AbstractActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    public LinkFinderActor() {
        receive(ReceiveBuilder
                        .match(WikipediaScanRequest.class, this::handleSourceRequest)
                        .matchAny(this::unhandled).build()
        );
    }

    private void handleSourceRequest(WikipediaScanRequest wikipediaScanRequest) {
        Jsoup.connect(wikipediaScanRequest.getUrl())
                .timeout(10000)
                .get();

        //matcher på regex \[\[(.*?)\]\] Må gjøre så den også matcher på | og litt slikt
        //bare handle interne linker? De er på formen:
        //gjøre om intern til ekstern link -> Ny actor?
        //Spawne nye actors for alle linker?
    }

}
