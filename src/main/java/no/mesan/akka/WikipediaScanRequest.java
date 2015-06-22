package no.mesan.akka;

public class WikipediaScanRequest {

    private final String url;

    public WikipediaScanRequest(final String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
