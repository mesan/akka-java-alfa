package no.mesan.akka.client;

import no.mesan.akka.common.RemoteWikipediaScanRequest;


public class WikipediaParserClientTest {

    private static final String wikipediaUrl = "https://en.wikipedia.org/wiki/Akka_(toolkit)";

    public static void main(final String[] args) {
        final WikipediaParserClient wikipediaParserClient = new WikipediaParserClient();
        final RemoteWikipediaScanRequest remoteWikipediaScanRequest = new RemoteWikipediaScanRequest(wikipediaUrl);
        wikipediaParserClient.scan(remoteWikipediaScanRequest);
    }
}