package no.mesan.akka.actors;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

public class ImageHandler extends AbstractActor {
    public ImageHandler() {
        receive(
                ReceiveBuilder.match(FoundImage.class, this::handleImage).matchAny(this::unhandled).build()
        );
    }

    private void handleImage(final FoundImage foundImage) {
        System.out.println(foundImage.getImageUrl());
        System.out.println(this.self());
    }
}
