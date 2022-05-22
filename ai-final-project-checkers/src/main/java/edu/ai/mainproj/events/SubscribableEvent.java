package edu.ai.mainproj.events;

import java.util.LinkedList;
import java.util.List;

public abstract class SubscribableEvent<T> {

    protected List<T> subscribers;

    public SubscribableEvent() {
        subscribers = new LinkedList<T>();
    }

    public boolean subscribe(T function) {
        if (subscribers.contains(function)) {
            return false;
        }
        subscribers.add(function);
        return true;
    }

    public boolean unsubscribe(T function) {
        return subscribers.remove(function);
    }

}
