package synchronization_keywords;

public class Calling_method {

    public synchronized void method_one(){
        System.out.println("我是周业好");
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void method_two(){
        System.out.println("我是班政");
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void method_three(){
        System.out.println("我是林水桥");
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void method_four(){

        synchronized (this){
            System.out.println("我是刘聪一号");
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void method_five(){

        synchronized (this){
            System.out.println("我是刘聪二号");
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void method_six(){

        synchronized (this){
            System.out.println("我是刘聪三号");
            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
