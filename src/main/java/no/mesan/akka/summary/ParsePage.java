package no.mesan.akka.summary;

/**
 * @author Knut Esten Melandsø Nekså
 */
public class ParsePage {
    private final String url;

    public ParsePage(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
