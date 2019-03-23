package lock_key;

public class Lock_three extends Thread{

    private Calling_method calling_method;


    public Lock_three(Calling_method calling_method) {
        this.calling_method = calling_method;
    }
    @Override
    public void run() {

        calling_method.lock_three();
    }
}
