package threads;

import static threads.ThreadColor.ANSI_GREEN;
import static threads.ThreadColor.ANSI_PURPLE;


public class ThreadsMain {

    public static void main(String[] args) {
        System.out.println(ANSI_GREEN + "First thread");
        Thread anotherThread = new AnotherThread();
        anotherThread.setName(">>>> ANOTHER THREAD INSTANCE <<<<");
        anotherThread.start();

        new Thread(){
            public void run(){
                System.out.println(ANSI_PURPLE + "The anonymous class output");
            }
        }.start();

        System.out.println(ANSI_GREEN + "Another notification from the main thread");

        Runnable runnable = new ThreadRunnable();
        runnable.run();
    }
}
