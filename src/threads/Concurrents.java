package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static threads.Concurrents.EOF;

public class Concurrents {

    public static final String EOF = "EOF";

    public static void main(String[] args) {
        List<String> buffer = new ArrayList<String>();
        MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_BLUE );
        MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_GREEN);
        MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE);

        new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();
    }
}

class MyProducer implements Runnable{
    private List<String> buffer;
    private String colour;

    MyProducer(List<String> buffer, String colour){
       this.buffer = buffer;
       this.colour = colour;
    }
    public void run() {
        Random random = new Random();
        String nums[] = {
              "one", "two", "three", "four", "five", "six"
        };

        for(String num : nums){
            try{
                System.out.println(colour + "Adding " + num);
                buffer.add(num);
                Thread.sleep(random.nextInt(1000));
            }catch(InterruptedException e){
                System.out.println("Producer interrupted");
            }
        }
        buffer.add("EOF");
    }
}

class MyConsumer implements Runnable{

    private List<String> buffer;
    private String color;

    MyConsumer(List<String> buffer, String color){
        this.buffer = buffer;
        this.color = color;
    }

    public void run(){
        while(true){
            if(buffer.isEmpty()){
                continue;
            }
            if(buffer.get(0).equals(EOF)){
                System.out.println(color + "Exiting");
                break;
            }else{
                System.out.println(color + "Removed " + buffer.remove(0));
            }
        }
    }
}
