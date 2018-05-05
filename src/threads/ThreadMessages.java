package threads;

import java.util.Random;

public class ThreadMessages {

    public static void main(String[] args) {
        Message message = new Message();
        new Thread(new Writer(message)).start();
        new Thread(new Reader(message)).start();
    }
}

class Message{
    private String message;
    private boolean empty = true;

    public synchronized String read(){
        while(empty){
            try{
                wait();
            }catch(InterruptedException e){

            }
        }
        empty = true;
        return message;
    }

    public synchronized void write(String text){
        while(!empty){
            try{
                wait();
            }catch(InterruptedException e){

            }
        }
        empty = false;
        this.message = text;
        notifyAll();
    }
}

class Writer implements Runnable{

    private Message message;

    public Writer(Message message){
        this.message = message;
    }

    public void run(){
        String messages[] = {
             "I am a message number 1",
             "I am a message number 2",
             "I am a message number 3",
             "I am a message number 4"
        };

        Random random = new Random();
        for(int i=0; i< messages.length; i++){
            message.write(messages[i]);
            try{
                Thread.sleep(random.nextInt(2000));
            }catch(InterruptedException e){
                System.out.println("ERROR");
                e.printStackTrace();
            }
        }
        message.write("Finished");
    }
}

class Reader implements Runnable{

    private Message message;

    public Reader(Message text){
        this.message = text;
    }

    public void run(){
        Random random = new Random();

        for(String latestMessage = message.read(); !latestMessage.equals("Finished");latestMessage = message.read()){
            System.out.println(latestMessage);
            try{
                Thread.sleep(random.nextInt(2000));
            }catch(InterruptedException e){

            }
        }
    }
}
