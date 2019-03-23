package lock_key;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Calling_method {

    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void lock_one(){

        synchronized ("a"){
            Lock writeLock = reentrantReadWriteLock.writeLock();
            writeLock.lock();
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("lock_one");
            writeLock.unlock();
        }
    }

    public void lock_two(){

        synchronized ("a"){
            Lock writeLock = reentrantReadWriteLock.writeLock();
            writeLock.lock();
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("lock_two");
            writeLock.unlock();
        }
    }

    public void lock_three(){

        synchronized ("a"){
            Lock readLock = reentrantReadWriteLock.readLock();
            readLock.lock();
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("lock_three");
            readLock.unlock();
        }
    }
}
