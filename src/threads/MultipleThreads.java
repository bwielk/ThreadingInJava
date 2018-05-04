package threads;

public class MultipleThreads {

    public static void main(String[] args) {
        CountDown cd = new CountDown();

        CountDownThread thread1 = new CountDownThread(cd);
        thread1.setName("Thread 1");
        thread1.start();

        CountDownThread thread2 = new CountDownThread(cd);
        thread2.setName("Thread 2");
        thread2.start();
    }
}

class CountDown{
    public void doCountdown(){
        String color;
        switch(Thread.currentThread().getName()){
            case "Thread 1":
                color = ThreadColor.ANSI_BLUE;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_GREEN;
                break;
            default:
                color = ThreadColor.ANSI_RED;
                break;
        }
        for(int i=10; i>0; i--){
            System.out.println(color + Thread.currentThread().getName() + " i=" + i);
        }
    }
}

class CountDownThread extends Thread{
    private CountDown threadCountDown;

    public CountDownThread(CountDown cd){
        this.threadCountDown = cd;
    }

    public void run(){
        threadCountDown.doCountdown();
    }
}