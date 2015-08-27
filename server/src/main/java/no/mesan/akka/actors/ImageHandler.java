package no.mesan.akka.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
public class ImageHandler extends AbstractActor {
    public ImageHandler() {
        receive(
                ReceiveBuilder.match(FoundImage.class, this::handleImage).matchAny(this::unhandled).build()
        );
    }

    private void handleImage(final FoundImage foundImage) {
        Image image = null;
        try {
            URL url = new URL(foundImage.getImageUrl());
            image = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't download", e);
        }
        context().sender().tell(image, context().self());
    }
}
