package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static threads.Concurrents.EOF;

public class Concurrents {

    public static final String EOF = "EOF";

    public static void main(String[] args) {
        ArrayBlockingQueue<String> buffer = new ArrayBlockingQueue<String>(7);
        //ReentrantLock bufferLock = new ReentrantLock();
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_BLUE/*, bufferLock*/);
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_GREEN/*, bufferLock*/);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE/*, bufferLock*/);

        //NO USE OF EXECUTOR
//        new Thread(producer).start();
//        new Thread(consumer1).start();
//        new Thread(consumer2).start();

        //EXECUTOR
        executorService.execute(producer);
        executorService.execute(consumer1);
        executorService.execute(consumer2);

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(ThreadColor.ANSI_RED + "Printed from the callable class");
                return "This is the callable result ";
            }
        });

        try{
            System.out.println(future.get());
        }catch(InterruptedException e){
            System.out.println("Something went wrong: Interrupted Exception");
            e.printStackTrace();
        }catch(ExecutionException e){
            System.out.println("Something went wrong: Execution Exception");
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}

class MyProducer implements Runnable{
    private ArrayBlockingQueue<String> buffer;
    private String colour;
    //private ReentrantLock bufferLock;

    MyProducer(ArrayBlockingQueue<String> buffer, String colour){
       this.buffer = buffer;
       this.colour = colour;
       //this.bufferLock = bufferLock;
    }
    public void run() {
        Random random = new Random();
        String nums[] = {
              "one", "two", "three", "four", "five", "six"
        };

        for(String num : nums){
            try{
                System.out.println(colour + "Adding " + num);
                //bufferLock.lock();
                buffer.put(num);
//                try{
//                    buffer.add(num);
//                }finally{
//                    bufferLock.unlock();
//                }
                Thread.sleep(random.nextInt(1000));
            }catch(InterruptedException e){
                System.out.println("Producer interrupted");
            }
        }
        //bufferLock.lock();
        try{
            buffer.put("EOF");
//        }finally{
//            bufferLock.unlock();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

class MyConsumer implements Runnable{

    private ArrayBlockingQueue<String> buffer;
    private String color;
    //private ReentrantLock bufferLock;

    MyConsumer(ArrayBlockingQueue<String> buffer, String color){
        this.buffer = buffer;
        this.color = color;
        //this.bufferLock = bufferLock;
    }

    public void run(){
        //int errorCounter = 0;
        while(true){
            synchronized (buffer) {
                // if(bufferLock.tryLock()){
                try {
                    if (buffer.isEmpty()) {
                        continue;
                    }
                    //System.out.println(color + "The counter = " + errorCounter);
                    //errorCounter = 0;
                    if (buffer.peek().equals(EOF)) {
                        System.out.println(color + "Exiting");
                        break;
                    } else {
                        System.out.println(color + "Removed " + buffer.take());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                }finally{
//                    bufferLock.unlock();
////                }
//            }else{
//                errorCounter += 1;
//            }
            }
        }
    }
}
