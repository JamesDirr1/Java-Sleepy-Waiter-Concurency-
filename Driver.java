//James Dirr CSC-460-001 Program #1
//Simulates a restaurant using multiple threaded classes
import java.util.*;
import  java.util.concurrent.Semaphore;

public class Driver {
    //Semaphores
    private volatile static Semaphore Door;
    private volatile static Semaphore Servicing;
    private volatile static Semaphore Nap;

    //Random number between 50 and 700
    public static int random(){
        return 50 + (int)(Math.random()*(651));
    }
    //Waits for enter key before continuing.
    public static void enterKey(){
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }
    //main
    public static void main (String [] args) throws InterruptedException {
       //instantiates semaphores
        Door = new Semaphore(15, true);
        Servicing = new Semaphore(0, true);
        Nap = new Semaphore(0, true);
        //empty array for threads of size 100
        Thread customers[] = new Thread[100];
        //loop that creates 100 customer threads and adds them to the array
        for (int i = 0; i < 100; i++ ){
            customers[i] = new Thread(new Customer(Nap, Servicing, Door));
        }
        //creates waiter thread
        Waiter waiter = new Waiter(Nap, Servicing);
        System.out.println("Hit ENTER to start rush hour simulation");
        enterKey();
        //starts waiter
        waiter.start();
        Thread.sleep(1000);
        //loop to start first 50 customer threads
        for(int j = 0; j < 50; j++){
            customers[j].start();
        }
        //loop that joins first 50 customer threads
        for(int p = 0; p < 50; p++){
            customers[p].join();
        }
        System.out.println("Hit ENTER to start slow time simulation");
        enterKey();
        //loop that start the last 50 customers will sleeping a random time in beteen each
        for(int t = 50; t < 100; t++){
            Thread.sleep(random());
            customers[t].start();
        }
        for(int w = 50; w < 100; w++){
            customers[w].join();
        }
        //interrupts waiter
        waiter.interrupt();
    }
}//end
