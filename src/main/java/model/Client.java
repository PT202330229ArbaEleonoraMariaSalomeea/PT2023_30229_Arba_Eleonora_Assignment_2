package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private int id;
    private int arrivalTime;
    private int serviceTime;

    public Client(int id, int lowArrival, int highArrival, int lowService, int highService ) {
        this.arrivalTime = randomNumber(lowArrival, highArrival);
        this.id = id;
        this.serviceTime = randomNumber(lowService,highService);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public synchronized int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }


    public int randomNumber(int low, int high){
        int Random;
        Random = low +  (int)(Math.random()*(high-low));
       return Random;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", arrivalTime=" + arrivalTime +
                ", serviceTime=" + serviceTime +
                '}';
    }
}
