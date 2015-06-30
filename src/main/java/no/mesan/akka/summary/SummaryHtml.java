package no.mesan.akka.summary;

import no.mesan.akka.actors.ActorMessage;
import org.jsoup.nodes.Element;

public class SummaryHtml extends ActorMessage<Element> {
    public SummaryHtml(Element html) {
        super(html);
    }

    public Element getHtml() {
        return getContents();
    }
}
