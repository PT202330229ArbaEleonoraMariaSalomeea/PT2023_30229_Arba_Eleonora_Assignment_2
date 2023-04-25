package BusinessLogic;

import model.Client;
import model.Server;

import java.util.ArrayList;

public class Scheduler {
    private ArrayList<Server> Store = new ArrayList<>();
    private int NumberOfQueues;
    private int maxClients;
    private Strategy strategy;


    public Scheduler(int NumberOfQueues){
        this.Store= new ArrayList<>();

        for(int i=0; i< NumberOfQueues; i++){
            Server newServer = new Server();
            Store.add(newServer);
        }

        for (Server serverAux : Store) {
            Thread t = new Thread(serverAux);
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy== SelectionPolicy.SHORTEST_QUEUE){
            strategy= new ShortestQueueStrategy();
        }

        if(policy== SelectionPolicy.SHORTEST_TIME){
            strategy= new TimeStrategy();
        }
    }

    public void dispatchTask(Client client){
        strategy.addTask(Store, client);
    }

    public ArrayList<Server> getStore() {
        return Store;
    }

    public void setStore(ArrayList<Server> store) {
        Store = store;
    }

    public int getNumberOfQueues() {
        return NumberOfQueues;
    }

    public void setNumberOfQueues(int numberOfQueues) {
        NumberOfQueues = numberOfQueues;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
