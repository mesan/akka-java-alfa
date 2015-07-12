package no.mesan.akka.actors;

public abstract class ActorMessage<T> {
    private final T contents;

    protected ActorMessage(T contents) {
        this.contents = contents;
    }

    public T getContents() {
        return contents;
    }
}
