package no.mesan.akka.summary;

/**
 * @author Knut Esten Melandsø Nekså
 */
public class HTMLReceived {
    private final String html;

    public HTMLReceived(String html) {
        this.html = html;
    }

    public String getHtml() {
        return html;
    }
}
