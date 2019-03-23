package lock_key;

public class Main {
    private static  Calling_method calling_method = new Calling_method();

    public static void main(String[] args) {

        new Lock_one(calling_method).start();

        new Lock_two(calling_method).start();

        new Lock_three(calling_method).start();

    }


}
