package synchronization_keywords;

public class Main {

    public static void main(String[] args) {
        Calling_method calling_method = new Calling_method();
        //同步方法
        Thread thread_one = new Thread(new Thread_one(calling_method));
        Thread thread_two = new Thread(new Thread_two(calling_method));
        Thread thread_three =  new Thread(new Thread_three(calling_method));

        //同步块
        Thread thread_four = new Thread(new Thread_four(calling_method));
        Thread thread_five = new Thread(new Thread_five(calling_method));
        Thread thread_six =  new Thread(new Thread_six(calling_method));
        System.out.println(thread_one.getState());
        thread_one.start();
        System.out.println(thread_one.getState());

        thread_two.start();
        thread_three.start();

        thread_four.start();
        thread_five.start();
        thread_six.start();
    }

}
