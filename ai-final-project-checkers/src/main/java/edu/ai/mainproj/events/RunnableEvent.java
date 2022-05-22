package edu.ai.mainproj.events;

public class RunnableEvent extends SubscribableEvent<Runnable> {

    public RunnableEvent() { super(); }

    public void broadcast() {
        for (Runnable subscriber : subscribers) {
            subscriber.run();
        }
    }

}
