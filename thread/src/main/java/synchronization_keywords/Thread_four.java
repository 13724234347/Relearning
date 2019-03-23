package synchronization_keywords;

public class Thread_four implements Runnable {

    private Calling_method calling_method;

    public Thread_four(Calling_method calling_method) {
        this.calling_method = calling_method;
    }

    @Override
    public void run() {
        calling_method.method_four();
    }
}
