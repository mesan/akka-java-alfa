package no.mesan.akka.client;

import no.mesan.akka.common.RemoteWikipediaScanRequest;

import java.util.Scanner;

/**
 * @author Knut Esten Melandsø Nekså
 */
public class ConsoleApp {
    public static void main(String[] args) {
        new ConsoleApp();
    }

    public ConsoleApp() {
        requestUrl();
    }

    private void requestUrl() {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter url: ");
        String url = in.nextLine();

        System.out.println("Set depth: ");
        int depth = in.nextInt();

        System.out.println();
        System.out.format("Scanning %s with depth %d", url, depth);
        System.out.println();
        System.out.println();

        final WikipediaParserClient wikipediaParserClient = new WikipediaParserClient();
        final RemoteWikipediaScanRequest scanRequest = new RemoteWikipediaScanRequest(url);
        wikipediaParserClient.scan(scanRequest);
        System.out.println("Scanning...");
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
