package no.mesan.akka;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.common.RemoteWikipediaScanRequest;
/**
 * Created by arnfinng on 28/08/2015.
 */
public class WikipediaParserServer extends AbstractActor{
    public WikipediaParserServer() {
        receive(ReceiveBuilder
                .match(WikipediaScanRequest.class, this::handleScanRequest)
                .match(RemoteWikipediaScanRequest.class, this::handleRemoteScanRequest)
                .matchAny(this::unhandled).build());
    }
    private void handleScanRequest(final WikipediaScanRequest wikipediaScanRequest) {
        System.out.println(wikipediaScanRequest.getContents());
        context().actorOf(Props.create(WikipediaParserMaster.class)).tell(wikipediaScanRequest, self());
    }

    private void handleRemoteScanRequest(final RemoteWikipediaScanRequest request) {
        ActorRef remoteCaller = sender();
        handleScanRequest(new WikipediaScanRequest(request.getUrl(), request.getDepth(), remoteCaller));
    }
}
