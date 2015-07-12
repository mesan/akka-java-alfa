package no.mesan.akka.actors;

public class FoundImage extends ActorMessage<String> {
    public FoundImage(final String imageUrl) {
        super(imageUrl);
    }

    public String getImageUrl() {
        return getContents();
    }
}
