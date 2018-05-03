package threads;

import javax.sound.midi.Soundbank;

import static threads.ThreadColor.ANSI_GREEN;
import static threads.ThreadColor.ANSI_PURPLE;
import static threads.ThreadColor.ANSI_RED;


public class ThreadsMain {

    public static void main(String[] args) {
        System.out.println(ANSI_GREEN + "First thread");
        Thread anotherThread = new AnotherThread();
        anotherThread.setName(">>>> ANOTHER THREAD INSTANCE <<<<");
        anotherThread.start();

        new Thread(){
            public void run(){
                System.out.println(ANSI_PURPLE + "The anonymous class output");
                try{
                    anotherThread.join(3000);
                    System.out.println(ANSI_RED + "AnotherThread terminated. I am running.");
                }catch(InterruptedException e){
                    System.out.println(ANSI_RED + "I was interrupted for some reason. Couldn't wait");
                    e.printStackTrace();
                }
            }
        }.start();

        System.out.println(ANSI_GREEN + "Another notification from the main thread");

        Runnable runnable = new ThreadRunnable();
        runnable.run();
        //anotherThread.interrupt();
    }
}
