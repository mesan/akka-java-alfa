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

        context().sender().tell(foundImage, context().self());
    }
}
