package patterns.resultcallback;

public interface ResultCallback<T> {
    void onResult(T result);
    void onError(String e);
}