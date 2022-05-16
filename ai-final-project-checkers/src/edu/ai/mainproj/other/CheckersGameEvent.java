package edu.ai.mainproj.other;

import java.util.LinkedList;
import java.util.List;

public class CheckersGameEvent {

    private List<Runnable> subscribers;

    public CheckersGameEvent() {
        subscribers = new LinkedList<Runnable>();
    }

    public void broadcast() {
        for (Runnable subscriber : subscribers) {
            subscriber.run();
        }
    }

    public boolean subscribe(Runnable f) {
        if (subscribers.contains(f)) {
            return false;
        }
        subscribers.add(f);
        return true;
    }

    public boolean unsubscribe(Runnable f) {
        return subscribers.remove(f);
    }

}
