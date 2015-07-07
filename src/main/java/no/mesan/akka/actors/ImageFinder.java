package no.mesan.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaScanRequest;
import org.jsoup.Jsoup;

import java.io.IOException;

public class ImageFinder extends AbstractActor {

    public ImageFinder() {
        receive(ReceiveBuilder
                        .match(WikipediaScanRequest.class, this::findImages)
                        .match(HandledImage.class, this::processImageHandled)
                        .matchAny(this::unhandled)
                        .build()
        );
    }

    private void findImages(final WikipediaScanRequest wikipediaScanRequest) throws IOException {
        System.out.println("url " + wikipediaScanRequest.getUrl());
        Jsoup.connect(wikipediaScanRequest.getUrl())
                .timeout(10000)
                .get()
                .select("img[src]")
                .stream()
                .map((image) -> image.attr("abs:src"))
                .map(FoundImage::new)
                .forEach((foundImage) -> context().actorOf(Props.create(ImageHandler.class))
                        .tell(foundImage, context().self()));

    }

    private void processImageHandled(final HandledImage handledImage) {
        System.out.println("Completed handling image " + handledImage.getContents());
    }
}
