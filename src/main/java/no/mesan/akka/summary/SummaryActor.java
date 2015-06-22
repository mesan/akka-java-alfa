package no.mesan.akka.summary;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author Knut Esten Melandsø Nekså
 */
public class SummaryActor extends AbstractActor {
    public SummaryActor() {
        receive(ReceiveBuilder
                        .match(String.class, this::findSummary)
                        .matchAny(this::unhandled).build()
        );
    }

    private void findSummary(final String url) {

        System.out.println(url);
    }
}
