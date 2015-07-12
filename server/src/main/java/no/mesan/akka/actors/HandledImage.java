package no.mesan.akka.actors;

public class HandledImage extends ActorMessage<String> {

    protected HandledImage(final String imageUrl) {
        super(imageUrl);
    }
}
