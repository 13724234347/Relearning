package synchronization_keywords;

public class Thread_two implements Runnable {

    private  Calling_method calling_method;

    public Thread_two(Calling_method calling_method){
        this.calling_method= calling_method;
    }

    @Override
    public void run() {
        calling_method.method_two();
    }
}
