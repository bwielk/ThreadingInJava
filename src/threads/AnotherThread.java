package threads;

import static threads.ThreadColor.ANSI_BLUE;

public class AnotherThread extends Thread{

    @Override
    public void run(){
        System.out.println(ANSI_BLUE + "Another thread from " + currentThread().getName());
    }
}
