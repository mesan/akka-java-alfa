package no.mesan.akka.common;

import java.io.Serializable;

public class RemoteWikipediaScanRequest implements Serializable {
    private final String url;

    public RemoteWikipediaScanRequest(final String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
