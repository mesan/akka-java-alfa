package no.mesan.akka.actors;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActorMessageTest {
    private class StringTestMessage extends ActorMessage<String> {
        public StringTestMessage(String value) {
            super(value);
        }

        public String getTestString() {
            return getContents();
        }
    }

    private class IntegerTestMessage extends ActorMessage<Integer> {
        public IntegerTestMessage(Integer value) {
            super(value);
        }

        public Integer getTestInteger() {
            return getContents();
        }
    }

    @Test
    public void keepsValueItWasInitializedWithForString() throws Exception {
        final String arbitraryString = "arbitrary string";
        StringTestMessage stringTestMessage = new StringTestMessage(arbitraryString);
        assertEquals(arbitraryString, stringTestMessage.getTestString());
    }

    @Test
    public void keepsValueItWasInitializedWithForInteger() throws Exception {
        final Integer arbitraryInteger = 42;
        IntegerTestMessage integerTestMessage = new IntegerTestMessage(arbitraryInteger);
        assertEquals(arbitraryInteger, integerTestMessage.getTestInteger());
    }
}

