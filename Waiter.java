//James Dirr CSC-460-001 Program #1
//Creates the waiter thread that nap if available or serves customers fcfs

import java.util.concurrent.Semaphore;

public class Waiter extends Thread{
    //Semaphores
    private volatile Semaphore Nap;
    private  volatile Semaphore Servicing;
    //Constructor
    public Waiter(Semaphore Nap, Semaphore Servicing){
        this.Nap = Nap;
        this.Servicing = Servicing;
    }
    //run method
    public void run(){
        //customer being served
        int customer = 0;
        //infinite loop
        do{
            //see if to nap or be awake
            if(!Nap.tryAcquire()){//if no one is waiting then nap
                System.out.println("Waiter is SLEEPING");
                try {
                    Nap.acquire();//blocks waiter forcing to wake up
                    System.out.println("Waiter is now AWAKE");
                } catch (InterruptedException e) {
                    return;
                }
            }
            //if not napping then serving
            try {
                //increase count for customer being served
                customer++;
                System.out.println("Waiter is servicing customer " + customer);
                //random number between 50 and 500
                int time = 50 + (int)(Math.random()*(451));
                //sleep as if time being spent serving
                Thread.sleep(time);
                //release customer when done serving
                Servicing.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(true);
    }
}
