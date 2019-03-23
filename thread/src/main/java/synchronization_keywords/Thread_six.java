package synchronization_keywords;

public class Thread_six implements Runnable{
    private Calling_method calling_method;

    public Thread_six(Calling_method calling_method) {
        this.calling_method = calling_method;
    }

    @Override
    public void run() {
        calling_method.method_six();
    }
}
