package com.vu;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CustomBlockingQueue<T> implements CustomBlockingQueueOperation<T>{
    private final Queue<T> queue;
    private final Lock lock;
    private final Condition notEmpty;

    public CustomBlockingQueue(){
        this.queue = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
    }
    @Override
    public void put(T element) throws InterruptedException {
        lock.lock();
        try {
            queue.add(element);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T take() throws InterruptedException {
        lock.lock();

        try{
            while(queue.isEmpty()){
                notEmpty.await();
            }
            return queue.poll();
        } finally{
            lock.unlock();
        }
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
