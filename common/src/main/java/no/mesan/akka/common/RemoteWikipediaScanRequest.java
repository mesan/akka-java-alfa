package no.mesan.akka.common;

import java.io.Serializable;

public class RemoteWikipediaScanRequest implements Serializable {
    private final String url;

    private int depth;

    public RemoteWikipediaScanRequest(final String url) {
        this.depth = 2;
        this.url = url;
    }
    public RemoteWikipediaScanRequest(final String url, int depth) {
        this.url = url;
        this.depth = depth;
    }
    public String getUrl() {
        return url;
    }
}
