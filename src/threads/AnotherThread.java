package threads;

import static threads.ThreadColor.ANSI_RED;
import static threads.ThreadColor.ANSI_BLUE;

public class AnotherThread extends Thread{

    @Override
    public void run(){
        System.out.println(ANSI_BLUE + "Another thread from " + currentThread().getName());
        try{
            Thread.sleep(6000);
        }catch(InterruptedException e){
            System.out.println(ANSI_RED + "Another thread woke me up");
            e.printStackTrace();
            return;
        }
        System.out.println(ANSI_BLUE + "Statement released after six seconds");
    }
}
