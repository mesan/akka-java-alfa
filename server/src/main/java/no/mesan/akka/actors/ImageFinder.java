package no.mesan.akka.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import no.mesan.akka.WikipediaScanRequest;
import org.jsoup.Jsoup;

import java.awt.*;
import java.io.IOException;

public class ImageFinder extends AbstractActor {

    public ImageFinder() {
        receive(ReceiveBuilder
                        .match(WikipediaScanRequest.class, this::findImages)
                        .match(FoundImage.class, this::processImageHandled)
                        .matchAny(this::unhandled)
                        .build()
        );
    }

    private void findImages(final WikipediaScanRequest wikipediaScanRequest) throws IOException {
        try {
            FoundImage foundImage = Jsoup.connect(wikipediaScanRequest.getContents())
                    .timeout(10000)
                    .get()
                    .select("img[src]")
                    .stream()
                    .map((image) -> image.attr("abs:src"))
                    .map(FoundImage::new).findFirst().orElse(null);
//                .forEach((foundImage) -> context().actorOf(Props.create(ImageHandler.class))
//                        .tell(foundImage, context().self()));
            processImageHandled(foundImage);
        }catch (Exception e){
            context().parent().tell(new FoundImage("https://abakus.no/uploads/events/thumbs/2012-01-08-2047-Mesan%20logo-578x150.jpg"), context().self());
        }
    }

    private void processImageHandled(final FoundImage handledImage) {
        context().parent().tell(handledImage, context().self());
    }
}
