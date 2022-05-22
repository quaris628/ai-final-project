package edu.ai.mainproj.events;

import java.util.function.Consumer;

public class ConsumerEvent<T> extends SubscribableEvent<Consumer<T>> {

    public ConsumerEvent() { super(); }

    public void broadcast(T arg) {
        for (Consumer<T> subscriber : subscribers) {
            subscriber.accept(arg);
        }
    }

}
