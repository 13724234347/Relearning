package lock_key;

public class Lock_one extends Thread{

    private Calling_method calling_method;

    public Lock_one(Calling_method calling_method) {
        this.calling_method = calling_method;
    }

    @Override
    public void run() {
        calling_method.lock_one();
    }
}
