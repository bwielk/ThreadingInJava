package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import static threads.Concurrents.EOF;

public class Concurrents {

    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<String>();
        ReentrantLock bufferLock = new ReentrantLock();
        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_BLUE, bufferLock);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_GREEN, bufferLock);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE, bufferLock);

        new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();
    }
}

class MyProducer implements Runnable{
    private List<String> buffer;
    private String colour;
    private ReentrantLock bufferLock;

    MyProducer(List<String> buffer, String colour, ReentrantLock bufferLock){
       this.buffer = buffer;
       this.colour = colour;
       this.bufferLock = bufferLock;
    }
    public void run() {
        Random random = new Random();
        String nums[] = {
              "one", "two", "three", "four", "five", "six"
        };

        for(String num : nums){
            try{
                System.out.println(colour + "Adding " + num);
                bufferLock.lock();
                buffer.add(num);
                bufferLock.unlock();
                Thread.sleep(random.nextInt(1000));
            }catch(InterruptedException e){
                System.out.println("Producer interrupted");
            }
        }
        bufferLock.lock();
        buffer.add("EOF");
        bufferLock.unlock();
    }
}

class MyConsumer implements Runnable{

    private List<String> buffer;
    private String color;
    private ReentrantLock bufferLock;

    MyConsumer(List<String> buffer, String color, ReentrantLock bufferLock){
        this.buffer = buffer;
        this.color = color;
        this.bufferLock = bufferLock;
    }

    public void run(){
        while(true){
            bufferLock.lock();
            if(buffer.isEmpty()){
                bufferLock.unlock();
                continue;
            }
            if(buffer.get(0).equals(EOF)){
                System.out.println(color + "Exiting");
                bufferLock.unlock();
                break;
            }else{
                System.out.println(color + "Removed " + buffer.remove(0));
            }
            bufferLock.unlock();
        }
    }
}
