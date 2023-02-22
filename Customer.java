//James Dirr CSC-460-001 Program #1
//Customer Thread that tries to enter restaurant  and waits to be served before leaving
import java.util.concurrent.Semaphore;

public class Customer extends Thread {
    //semaphores
    private volatile Semaphore Nap;
    private volatile Semaphore Servicing;
    private  volatile Semaphore Door;
    //for mutex on counter
    private volatile  Semaphore countmutex;
    //customer number
    private static int custCount = 1;
    //constructor
    public Customer(Semaphore Nap, Semaphore Servicing, Semaphore Door){
        this.Nap = Nap;
        this.Servicing = Servicing;
        this.Door = Door;
    }
    //run method
    public void run(){
        //instantiate countmutex semaphore
        countmutex = new Semaphore(1, true);
        System.out.println("New customer is attempting to enter restaurant");
        try {
            //attempts to enter restaurant
            Door.acquire();
            //attempts to get and update customer number
            countmutex.acquire();
            //gets customer number and increase by 1
            int custNum = custCount++;
            //release mutex counter
            countmutex.release();
            System.out.println("Customer " + custNum + " has entered the restaurant and is seated");
            //wakes up waiter or keeps him awake
            Nap.release();
            System.out.println("Customer " + custNum + " is waiting for the waiter");
            //request to be served
            Servicing.acquire();
            System.out.println("Customer " + custNum + " has been served");
            System.out.println("Customer " + custNum + " is leaving");
            //leaves allowing for more customers to enter
            Door.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
