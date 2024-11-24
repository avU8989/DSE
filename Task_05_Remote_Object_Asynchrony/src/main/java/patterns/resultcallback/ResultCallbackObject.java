package patterns.resultcallback;

import kickstart.IMarshall;
import kickstart.ResponseMessage;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResultCallbackObject<T> implements ResultCallback<T>, IMarshall {
    private ResultCallback<T> callback;
    private T result;
    private Exception error;
    private final Lock lock = new ReentrantLock();
    private final Condition resultAvailable = lock.newCondition();

    public ResultCallbackObject() {
    }

    public ResultCallbackObject(ResultCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onResult(T result) {
        lock.lock();
        try {
            this.result = result;
            resultAvailable.signalAll();
            if (callback != null) {
                callback.onResult(result);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onError(String error) {
        lock.lock();
        try {
            this.error = new Exception(error);
            resultAvailable.signalAll();
            if (callback != null) {
                callback.onError(error);
            }
        } finally {
            lock.unlock();
        }
    }

    public Exception getError() {
        return error;
    }
}