package lock_key;

public class Lock_two extends Thread{

    private Calling_method calling_method;


    public Lock_two(Calling_method calling_method) {
        this.calling_method = calling_method;
    }
    @Override
    public void run() {
        calling_method.lock_two();
    }
}
