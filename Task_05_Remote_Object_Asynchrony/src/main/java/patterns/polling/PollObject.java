package patterns.polling;

import kickstart.ResponseMessage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PollObject<T> {
    private volatile ResponseMessage<T> responseMessage;
    private final Lock lock = new ReentrantLock();
    private final Condition resultAvailable = lock.newCondition();

    public void update(ResponseMessage<T> response) {
        lock.lock();
        try {
            responseMessage = response;
            resultAvailable.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public ResponseMessage<T> awaitResult() throws InterruptedException {
        lock.lock();
        try {
            while (!isComplete()) {
                resultAvailable.await();
            }
            return responseMessage;
        } finally {
            lock.unlock();
        }
    }

    public boolean isComplete() {
        return responseMessage != null && responseMessage.getResponseData().equals("Operation completed");
    }
}
