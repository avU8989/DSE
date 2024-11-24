package com.vu;

public interface CustomBlockingQueueOperation<T> {
    void put(T element) throws InterruptedException;

    T take() throws InterruptedException;

    int size();

    boolean isEmpty();


}
