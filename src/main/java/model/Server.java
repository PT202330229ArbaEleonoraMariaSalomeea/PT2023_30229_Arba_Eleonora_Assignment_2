package model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Server implements Runnable{

    private BlockingQueue<Client> queue;
    private AtomicInteger waitingTime;
    private AtomicInteger avgWaitingTime;
    public Server() {
        this.queue = new LinkedBlockingQueue<>();
        this.waitingTime = new AtomicInteger();
        this.avgWaitingTime= new AtomicInteger(0);
    }

    @Override
    public String toString() {
        return "Server{" +
                "queue=" + queue +
                ", waitingTime=" + waitingTime +
                '}';
    }

    @Override
    public void run() {

        while(true){
            if(!this.queue.isEmpty()){
                if(this.queue.peek().getServiceTime() > 0) {
                   // System.out.println("client;" + queue.peek().getId()+ " " + queue.peek().getServiceTime());
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    waitingTime.decrementAndGet();
                    this.queue.peek().setServiceTime(this.queue.peek().getServiceTime()-1);

                }
                else {
                    this.queue.remove(this.queue.peek());
                }
            }
        }
    }

    public BlockingQueue<Client> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<Client> queue) {
        this.queue = queue;
    }

    public AtomicInteger getWaitingTime() {
        return waitingTime;
    }

    public void addClient(Client newClient){
        queue.add(newClient);
        waitingTime.addAndGet(newClient.getServiceTime());
        avgWaitingTime.addAndGet(waitingTime.get());
    }

    public AtomicInteger getAvgWaitingTime() {
        return avgWaitingTime;
    }
}
