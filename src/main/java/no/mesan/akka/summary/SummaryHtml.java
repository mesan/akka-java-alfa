package no.mesan.akka.summary;

import org.jsoup.nodes.Element;

public class SummaryHtml {
    private final Element html;

    public SummaryHtml(Element html) {
        this.html = html;
    }

    public Element getHtml() {
        return html;
    }
}
