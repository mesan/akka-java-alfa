package no.mesan.akka.actors;

public class FoundImage {

    private final String imageUrl;

    public FoundImage(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
