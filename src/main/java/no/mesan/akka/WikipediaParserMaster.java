package no.mesan.akka;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

public class WikipediaParserMaster extends AbstractActor {

    public WikipediaParserMaster() {
        receive(ReceiveBuilder
                        .match(WikipediaScanRequest.class, this::handleScanRequest)
                        .matchAny(this::unhandled).build()
        );
    }

    private void handleScanRequest(final WikipediaScanRequest wikipediaScanRequest) {
        System.out.println("Starter scan av url: " + wikipediaScanRequest.getUrl());
    }
}
