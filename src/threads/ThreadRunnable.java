package threads;

public class ThreadRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println(ThreadColor.ANSI_CYAN + "Thread with runnable interface");
    }
}
