package no.mesan.akka.summary;

import no.mesan.akka.actors.ActorMessage;
import org.jsoup.nodes.Element;

public class SummaryAsHtml extends ActorMessage<Element> {
    public SummaryAsHtml(Element html) {
        super(html);
    }

    public Element getHtml() {
        return getContents();
    }
}
