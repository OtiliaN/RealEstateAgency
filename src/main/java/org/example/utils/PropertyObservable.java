package org.example.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PropertyObservable {
    private final List<PropertyObserver> observers = new CopyOnWriteArrayList<>();

    public void addObserver(PropertyObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(PropertyObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (PropertyObserver observer : observers) {
            observer.onPropertyListChanged();
        }
    }
}
